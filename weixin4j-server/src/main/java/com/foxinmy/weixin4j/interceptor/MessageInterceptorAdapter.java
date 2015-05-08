package com.foxinmy.weixin4j.interceptor;

import io.netty.channel.ChannelHandlerContext;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

public abstract class MessageInterceptorAdapter implements
		WeixinMessageInterceptor {

	@Override
	public boolean preHandle(ChannelHandlerContext ctx, WeixinRequest request,
			WeixinMessage message, WeixinMessageHandler handler)
			throws WeixinException {
		return true;
	}

	@Override
	public void postHandle(ChannelHandlerContext ctx, WeixinRequest request,
			WeixinResponse response, WeixinMessage message,
			WeixinMessageHandler handler) throws WeixinException {
	}

	@Override
	public void afterCompletion(ChannelHandlerContext ctx,
			WeixinRequest request, WeixinMessage message,
			WeixinMessageHandler handler, WeixinException exception)
			throws WeixinException {
	}
}
