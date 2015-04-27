package com.foxinmy.weixin4j.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.message.HttpWeixinMessage;

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
		
		String xmlContent = httpMessage.getOriginalContent();
		
		
		/*
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
					httpMessage.getSignature());
		}
		if (!validate) {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.FORBIDDEN));
			return;
		}
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
		}*/
	}
}
