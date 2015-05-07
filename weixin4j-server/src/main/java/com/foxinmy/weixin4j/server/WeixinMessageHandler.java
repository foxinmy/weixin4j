package com.foxinmy.weixin4j.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.message.HttpWeixinMessage;
import com.foxinmy.weixin4j.message.ResponseMessage;
import com.foxinmy.weixin4j.message.WeixinMessage;
import com.foxinmy.weixin4j.model.AesToken;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.HttpUtil;
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
public class WeixinMessageHandler extends
		SimpleChannelInboundHandler<HttpWeixinMessage> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final AesToken aesToken;
	private final JAXBContext jaxbContext;

	public WeixinMessageHandler(AesToken aesToken) throws JAXBException {
		this.aesToken = aesToken;
		jaxbContext = JAXBContext.newInstance(WeixinMessage.class);
	}

	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
		log.error("catch the exception:{}", cause.getMessage());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			HttpWeixinMessage httpMessage) throws Exception {
		log.info("\n=================message in=================\n{}",
				httpMessage);
		if (httpMessage.getMethod().equals(HttpMethod.GET.name())) {
			if (MessageUtil.signature(aesToken.getToken(),
					httpMessage.getTimeStamp(), httpMessage.getNonce()).equals(
					httpMessage.getSignature())) {
				ctx.write(HttpUtil.createWeixinMessageResponse(
						httpMessage.getEchoStr(), Consts.CONTENTTYPE$TEXT_PLAIN));
				return;
			}
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.FORBIDDEN));
			return;
		} else if (httpMessage.getMethod().equals(HttpMethod.POST.name())) {
			if (!MessageUtil.signature(aesToken.getToken(),
					httpMessage.getTimeStamp(), httpMessage.getNonce()).equals(
					httpMessage.getSignature())) {
				ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
						HttpResponseStatus.FORBIDDEN));
				return;
			}
			if (httpMessage.getEncryptType() == EncryptType.AES) {
				if (!MessageUtil.signature(aesToken.getToken(),
						httpMessage.getTimeStamp(), httpMessage.getNonce(),
						httpMessage.getEncryptContent()).equals(
						httpMessage.getMsgSignature())) {
					ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
							HttpResponseStatus.FORBIDDEN));
					return;
				}
			}
		} else {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.METHOD_NOT_ALLOWED));
			return;
		}
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		WeixinMessage weixinMessage = (WeixinMessage) jaxbUnmarshaller
				.unmarshal(new ByteArrayInputStream(httpMessage
						.getOriginalContent().getBytes(Consts.UTF_8)));
		/*
		 * if (action == null) { ctx.write(new
		 * DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
		 * HttpResponseStatus.NOT_FOUND)); return; }
		 */
		ResponseMessage response = new ResponseMessage(weixinMessage,
				new TextResponse("Hello World!"));
		log.info("\n=================message out=================\n{}",
				response);
		/*
		 * if (response == null) {
		 * ctx.write(HttpUtil.createWeixinMessageResponse(Consts.SUCCESS,
		 * Consts.CONTENTTYPE$TEXT_PLAIN)); return; }
		 */
		if (httpMessage.getEncryptType() == EncryptType.RAW) {
			ctx.write(HttpUtil.createWeixinMessageResponse(response.toXml(),
					Consts.CONTENTTYPE$APPLICATION_XML));
		} else {
			ctx.write(response);
		}
	}
}
