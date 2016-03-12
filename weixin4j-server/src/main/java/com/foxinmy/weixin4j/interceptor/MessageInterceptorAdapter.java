package com.foxinmy.weixin4j.interceptor;

import io.netty.channel.ChannelHandlerContext;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 消息拦截适配
 * 
 * @className MessageInterceptorAdapter
 * @author jy
 * @date 2015年5月14日
 * @since JDK 1.6
 * @see
 */
public abstract class MessageInterceptorAdapter implements
		WeixinMessageInterceptor {

	@Override
	public boolean preHandle(ChannelHandlerContext context,
			WeixinRequest request, WeixinMessage message, WeixinMessageHandler handler)
			throws WeixinException {
		return true;
	}

	@Override
	public void postHandle(ChannelHandlerContext context,
			WeixinRequest request, WeixinResponse response, WeixinMessage message,
			WeixinMessageHandler handler) throws WeixinException {
	}

	@Override
	public void afterCompletion(ChannelHandlerContext context,
			WeixinRequest request, WeixinResponse response, WeixinMessage message,
			WeixinMessageHandler handler, Exception exception)
			throws WeixinException {
	}

	public int weight() {
		return 0;
	}
}
