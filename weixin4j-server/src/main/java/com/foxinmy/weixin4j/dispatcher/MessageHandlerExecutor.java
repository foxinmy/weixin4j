package com.foxinmy.weixin4j.dispatcher;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 微信消息分发器
 * 
 * @className MessageHandlerExecutor
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
 */
public class MessageHandlerExecutor {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 消息处理器
	 */
	private final WeixinMessageHandler messageHandler;

	/**
	 * 消息拦截器
	 */
	private final WeixinMessageInterceptor[] messageInterceptors;

	private int interceptorIndex = -1;

	public MessageHandlerExecutor(WeixinMessageHandler messageHandler,
			WeixinMessageInterceptor[] messageInterceptors) {
		this.messageHandler = messageHandler;
		this.messageInterceptors = messageInterceptors;
	}

	public WeixinMessageHandler getMessageHandler() {
		return messageHandler;
	}

	public boolean applyPreHandle(ChannelHandlerContext ctx,
			WeixinRequest request, WeixinMessage message)
			throws WeixinException {
		if (messageInterceptors != null) {
			for (int i = 0; i < messageInterceptors.length; i++) {
				WeixinMessageInterceptor interceptor = messageInterceptors[i];
				if (!interceptor.preHandle(ctx, request, message,
						messageHandler)) {
					triggerAfterCompletion(ctx, request, message, null);
					return false;
				}
				this.interceptorIndex = i;
			}
		}
		return true;
	}

	public void applyPostHandle(ChannelHandlerContext ctx,
			WeixinRequest request, WeixinResponse response,
			WeixinMessage message) throws WeixinException {
		if (messageInterceptors == null) {
			return;
		}
		for (int i = messageInterceptors.length - 1; i >= 0; i--) {
			WeixinMessageInterceptor interceptor = messageInterceptors[i];
			interceptor.postHandle(ctx, request, response, message,
					messageHandler);
		}
	}

	public void triggerAfterCompletion(ChannelHandlerContext ctx,
			WeixinRequest request, WeixinMessage message, WeixinException ex)
			throws WeixinException {
		if (messageInterceptors == null) {
			return;
		}
		for (int i = this.interceptorIndex; i >= 0; i--) {
			WeixinMessageInterceptor interceptor = messageInterceptors[i];
			try {
				interceptor.afterCompletion(ctx, request, message,
						messageHandler, ex);
			} catch (WeixinException e) {
				logger.error(
						"MessageInterceptor.afterCompletion threw exception", e);
			}
		}
	}
}
