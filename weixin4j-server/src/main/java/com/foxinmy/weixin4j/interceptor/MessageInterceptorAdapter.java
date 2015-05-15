package com.foxinmy.weixin4j.interceptor;

import io.netty.channel.ChannelHandlerContext;

import com.foxinmy.weixin4j.dispatcher.WeixinMessageAdapter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 消息拦截适配
 * 
 * @className MessageInterceptorAdapter
 * @author jy
 * @date 2015年5月14日
 * @since JDK 1.7
 * @see
 */
public abstract class MessageInterceptorAdapter<M> extends
		WeixinMessageAdapter<M> implements WeixinMessageInterceptor {

	private M message;

	@Override
	public boolean preHandle(ChannelHandlerContext context,
			WeixinRequest request, final String message,
			WeixinMessageHandler handler) throws WeixinException {
		this.message = super.messageRead(message);
		return preHandle0(context, request, this.message, handler);
	}

	public abstract boolean preHandle0(ChannelHandlerContext context,
			WeixinRequest request, M message, WeixinMessageHandler handler)
			throws WeixinException;

	@Override
	public void postHandle(ChannelHandlerContext context,
			WeixinRequest request, WeixinResponse response, String message,
			WeixinMessageHandler handler) throws WeixinException {
		postHandle0(context, request, response, message, this.message, handler);
	}

	public abstract void postHandle0(ChannelHandlerContext context,
			WeixinRequest request, WeixinResponse response, String message,
			M m, WeixinMessageHandler handler) throws WeixinException;

	@Override
	public void afterCompletion(ChannelHandlerContext context,
			WeixinRequest request, String message,
			WeixinMessageHandler handler, WeixinException exception)
			throws WeixinException {
		afterCompletion0(context, request, message, this.message, handler,
				exception);
	}

	public abstract void afterCompletion0(ChannelHandlerContext context,
			WeixinRequest request, String message, M m,
			WeixinMessageHandler handler, WeixinException exception)
			throws WeixinException;
}
