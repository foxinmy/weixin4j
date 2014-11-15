package com.foxinmy.weixin4j.mp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.mp.action.WeixinAction;
import com.foxinmy.weixin4j.mp.mapping.ActionMapping;
import com.foxinmy.weixin4j.mp.model.HttpWeixinMessage;
import com.foxinmy.weixin4j.mp.response.BaseResponse;
import com.foxinmy.weixin4j.mp.type.EncryptType;
import com.foxinmy.weixin4j.mp.util.HttpUtil;
import com.foxinmy.weixin4j.util.MessageUtil;

public class WeixinServerHandler extends
		SimpleChannelInboundHandler<HttpWeixinMessage> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final ActionMapping actionMapping;

	public WeixinServerHandler(ActionMapping actionMapping) {
		this.actionMapping = actionMapping;
	}

	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			HttpWeixinMessage httpMessage) throws Exception {
		log.info("\n=================message in=================\n{}",
				httpMessage);
		boolean validate = false;
		if (httpMessage.getMethod() == HttpMethod.GET
				|| httpMessage.getEncryptType() == EncryptType.RAW) {
			validate = MessageUtil.signature(httpMessage.getToken(),
					httpMessage.getTimeStamp(), httpMessage.getNonce()).equals(
					httpMessage.getSignature());
			if (httpMessage.getMethod() == HttpMethod.GET && validate) {
				HttpResponse httpResponse = HttpUtil
						.createWeixinMessageResponse(httpMessage.getEchoStr());
				ctx.write(httpResponse);
				return;
			}
		} else {
			validate = MessageUtil.signature(httpMessage.getToken(),
					httpMessage.getTimeStamp(), httpMessage.getNonce(),
					httpMessage.getEncryptContent()).equals(
					httpMessage.getMsgSignature());
		}
		if (!validate) {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.FORBIDDEN));
			return;
		}

		String xmlContent = httpMessage.getXmlContent();
		WeixinAction action = actionMapping.getAction(xmlContent);
		if (action == null) {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.NOT_FOUND));
			return;
		}
		BaseResponse response = action.execute(xmlContent);
		log.info("\n=================message out=================\n{}",
				response);
		if (httpMessage.getEncryptType() == EncryptType.RAW) {
			HttpResponse httpResponse = HttpUtil
					.createWeixinMessageResponse(response.toXml());
			ctx.write(httpResponse);
		} else {
			ctx.write(response);
		}
	}
}
