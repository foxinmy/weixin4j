package com.foxinmy.weixin4j.mp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.action.WeixinAction;
import com.foxinmy.weixin4j.action.mapping.ActionMapping;
import com.foxinmy.weixin4j.mp.util.HttpUtil;
import com.foxinmy.weixin4j.response.HttpWeixinMessage;
import com.foxinmy.weixin4j.response.ResponseMessage;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.MessageUtil;

/**
 * 微信被动消息处理类
 * 
 * @className WeixinServerHandler
 * @author jy
 * @date 2014年11月16日
 * @since JDK 1.7
 * @see
 */
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
		boolean isGet = httpMessage.getMethod().equals(HttpMethod.GET.name());
		boolean validate = false;
		if (isGet || httpMessage.getEncryptType() == EncryptType.RAW) {
			validate = MessageUtil.signature(httpMessage.getToken(),
					httpMessage.getTimeStamp(), httpMessage.getNonce()).equals(
					httpMessage.getSignature());
			if (isGet && validate) {
				ctx.write(HttpUtil.createWeixinMessageResponse(
						httpMessage.getEchoStr(), ContentType.TEXT_PLAIN));
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
		ResponseMessage response = action.execute(xmlContent);
		log.info("\n=================message out=================\n{}",
				response);
		if (response == null) {
			ctx.write(HttpUtil.createWeixinMessageResponse("",
					ContentType.TEXT_PLAIN));
			return;
		}
		if (httpMessage.getEncryptType() == EncryptType.RAW) {
			ctx.write(HttpUtil.createWeixinMessageResponse(response.toXml(),
					null));
		} else {
			ctx.write(response);
		}
	}
}
