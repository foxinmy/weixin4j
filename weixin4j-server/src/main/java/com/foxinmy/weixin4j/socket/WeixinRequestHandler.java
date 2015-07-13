package com.foxinmy.weixin4j.socket;

import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.type.EncryptType;
import com.foxinmy.weixin4j.util.AesToken;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.HttpUtil;
import com.foxinmy.weixin4j.util.MessageUtil;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.CruxMessageHandler;

/**
 * 微信请求处理类
 * 
 * @className WeixinRequestHandler
 * @author jy
 * @date 2014年11月16日
 * @since JDK 1.7
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
		logger.error("catch the exception:{}", cause);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, WeixinRequest request)
			throws WeixinException {
		final AesToken aesToken = request.getAesToken();
		if (request.getMethod().equals(HttpMethod.GET.name())) {
			if (MessageUtil.signature(aesToken.getToken(),
					request.getTimeStamp(), request.getNonce()).equals(
					request.getSignature())) {
				ctx.writeAndFlush(
						HttpUtil.createHttpResponse(request.getEchoStr(), OK,
								Consts.CONTENTTYPE$TEXT_PLAIN)).addListener(
						ChannelFutureListener.CLOSE);
				return;
			}
			ctx.writeAndFlush(
					HttpUtil.createHttpResponse(null, FORBIDDEN, null))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		} else if (request.getMethod().equals(HttpMethod.POST.name())) {
			if (!StringUtil.isBlank(request.getSignature())
					&& !MessageUtil.signature(aesToken.getToken(),
							request.getTimeStamp(), request.getNonce()).equals(
							request.getSignature())) {
				ctx.writeAndFlush(
						HttpUtil.createHttpResponse(null, FORBIDDEN, null))
						.addListener(ChannelFutureListener.CLOSE);
				return;
			}
			if (request.getEncryptType() == EncryptType.AES) {
				if (!MessageUtil.signature(aesToken.getToken(),
						request.getTimeStamp(), request.getNonce(),
						request.getEncryptContent()).equals(
						request.getMsgSignature())) {
					ctx.writeAndFlush(
							HttpUtil.createHttpResponse(null, FORBIDDEN, null))
							.addListener(ChannelFutureListener.CLOSE);
					return;
				}
			}
		} else {
			ctx.writeAndFlush(
					HttpUtil.createHttpResponse(null, METHOD_NOT_ALLOWED, null))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}
		CruxMessageHandler cruxMessage = CruxMessageHandler.parser(request
				.getOriginalContent());
		MessageTransfer messageTransfer = new MessageTransfer(aesToken,
				request.getEncryptType(), cruxMessage.getToUserName(),
				cruxMessage.getFromUserName());
		ctx.channel().attr(Consts.MESSAGE_TRANSFER_KEY).set(messageTransfer);
		messageDispatcher.doDispatch(ctx, request, cruxMessage);
	}
}
