package com.foxinmy.weixin4j.socket;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.SingleResponse;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.AesToken;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.ServerToolkits;
import com.foxinmy.weixin4j.xml.MessageTransferHandler;

/**
 * 微信请求处理类
 * 
 * @className WeixinRequestHandler
 * @author jy
 * @date 2014年11月16日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher
 */
public class WeixinRequestHandler extends
		SimpleChannelInboundHandler<WeixinRequest> {
	private final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	private final WeixinMessageDispatcher messageDispatcher;

	public WeixinRequestHandler(WeixinMessageDispatcher messageDispatcher) {
		this.messageDispatcher = messageDispatcher;
	}

	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		logger.error("catch the exception:", cause);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, WeixinRequest request)
			throws WeixinException {
		final AesToken aesToken = request.getAesToken();
		if (aesToken == null
				|| (ServerToolkits.isBlank(request.getSignature()) && ServerToolkits
						.isBlank(request.getMsgSignature()))) {
			ctx.writeAndFlush(resolveResponse(BAD_REQUEST, request))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}
		/**
		 * 公众平台:无论Get,Post都带signature参数,当开启aes模式时带msg_signature参数
		 * 企业号:无论Get,Post都带msg_signature参数
		 **/
		if (request.getMethod() == HttpMethod.GET) {
			if (!ServerToolkits.isBlank(request.getSignature())
					&& MessageUtil.signature(aesToken.getToken(),
							request.getTimeStamp(), request.getNonce()).equals(
							request.getSignature())) {
				ctx.write(new SingleResponse(request.getEchoStr()));
				return;
			}
			if (!ServerToolkits.isBlank(request.getMsgSignature())
					&& MessageUtil.signature(aesToken.getToken(),
							request.getTimeStamp(), request.getNonce(),
							request.getEchoStr()).equals(
							request.getMsgSignature())) {
				ctx.write(new SingleResponse(MessageUtil.aesDecrypt(null,
						aesToken.getAesKey(), request.getEchoStr())));
				return;
			}
			ctx.writeAndFlush(resolveResponse(FORBIDDEN, request)).addListener(
					ChannelFutureListener.CLOSE);
			return;
		} else if (request.getMethod() == HttpMethod.POST) {
			if (!ServerToolkits.isBlank(request.getSignature())
					&& !MessageUtil.signature(aesToken.getToken(),
							request.getTimeStamp(), request.getNonce()).equals(
							request.getSignature())) {
				ctx.writeAndFlush(resolveResponse(FORBIDDEN, request))
						.addListener(ChannelFutureListener.CLOSE);
				return;
			}
			if (request.getEncryptType() == EncryptType.AES
					&& !MessageUtil.signature(aesToken.getToken(),
							request.getTimeStamp(), request.getNonce(),
							request.getEncryptContent()).equals(
							request.getMsgSignature())) {
				ctx.writeAndFlush(resolveResponse(FORBIDDEN, request))
						.addListener(ChannelFutureListener.CLOSE);
				return;
			}
		} else {
			ctx.writeAndFlush(resolveResponse(METHOD_NOT_ALLOWED, request))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}
		WeixinMessageTransfer messageTransfer = MessageTransferHandler
				.parser(request);
		ctx.channel().attr(ServerToolkits.MESSAGE_TRANSFER_KEY)
				.set(messageTransfer);
		messageDispatcher.doDispatch(ctx, request, messageTransfer);
	}

	private FullHttpResponse resolveResponse(HttpResponseStatus responseStatus,
			WeixinRequest request) {
		FullHttpResponse response = new DefaultFullHttpResponse(
				request.getProtocolVersion(), responseStatus);
		HttpUtil.resolveHeaders(response);
		return response;
	}
}
