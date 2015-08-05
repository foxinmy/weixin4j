package com.foxinmy.weixin4j.interceptor;

import io.netty.channel.ChannelHandlerContext;

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
public abstract class MessageInterceptorAdapter implements
		WeixinMessageInterceptor {

	@Override
	public boolean preHandle(ChannelHandlerContext context,
			WeixinRequest request, Object message, WeixinMessageHandler handler)
			throws WeixinException {
		return true;
	}

	@Override
	public void postHandle(ChannelHandlerContext context,
			WeixinRequest request, WeixinResponse response, Object message,
			WeixinMessageHandler handler) throws WeixinException {
	}

	@Override
	public void afterCompletion(ChannelHandlerContext context,
			WeixinRequest request, WeixinResponse response, Object message,
			WeixinMessageHandler handler, Exception exception)
			throws WeixinException {
	}
}
