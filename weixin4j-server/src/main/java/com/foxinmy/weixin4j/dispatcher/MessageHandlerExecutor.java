package com.foxinmy.weixin4j.dispatcher;

import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 微信消息的处理执行
 *
 * @className MessageHandlerExecutor
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.handler.WeixinMessageHandler
 * @see com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor
 */
public class MessageHandlerExecutor {

	private final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());
	/**
	 * 消息处理器
	 */
	private final WeixinMessageHandler messageHandler;

	/**
	 * 消息拦截器
	 */
	private final WeixinMessageInterceptor[] messageInterceptors;
	/**
	 * 节点名称集合
	 */
	private final ChannelHandlerContext context;

	private int interceptorIndex = -1;

	public MessageHandlerExecutor(ChannelHandlerContext context,
			WeixinMessageHandler messageHandler,
			WeixinMessageInterceptor[] messageInterceptors) {
		this.context = context;
		this.messageHandler = messageHandler;
		this.messageInterceptors = messageInterceptors;
	}

	public WeixinMessageHandler getMessageHandler() {
		return messageHandler;
	}

	/**
	 * 执行预拦截动作
	 *
	 * @param request
	 *            微信请求信息
	 * @param message
	 *            微信消息
	 * @return true则继续执行往下执行
	 * @throws WeixinException
	 */
	public boolean applyPreHandle(WeixinRequest request, WeixinMessage message){
		if (messageInterceptors != null) {
			for (int i = 0; i < messageInterceptors.length; i++) {
				WeixinMessageInterceptor interceptor = messageInterceptors[i];
				if (!interceptor.preHandle(context, request, message,
						messageHandler)) {
					triggerAfterCompletion(request, null, message, null);
					return false;
				}
				this.interceptorIndex = i;
			}
		}
		return true;
	}

	/**
	 * MessageHandler处理玩请求后的动作
	 *
	 * @param request
	 *            微信请求
	 * @param response
	 *            处理后的响应
	 * @param message
	 *            微信消息
	 * @throws WeixinException
	 */
	public void applyPostHandle(WeixinRequest request, WeixinResponse response,
			WeixinMessage message){
		if (messageInterceptors == null) {
			return;
		}
		for (int i = messageInterceptors.length - 1; i >= 0; i--) {
			WeixinMessageInterceptor interceptor = messageInterceptors[i];
			interceptor.postHandle(context, request, response, message,
					messageHandler);
		}
	}

	/**
	 * 全部执行完毕后触发
	 *
	 * @param request
	 *            微信请求
	 * @param response
	 *            微信响应 可能为空
	 * @param message
	 *            微信消息
	 * @param exception
	 *            处理时可能的异常
	 * @throws WeixinException
	 */
	public void triggerAfterCompletion(WeixinRequest request,
			WeixinResponse response, WeixinMessage message, Exception exception)
		 {
		if (messageInterceptors == null) {
			return;
		}
		for (int i = this.interceptorIndex; i >= 0; i--) {
			WeixinMessageInterceptor interceptor = messageInterceptors[i];
			try {
				interceptor.afterCompletion(context, request, response,
						message, messageHandler, exception);
			} catch (Exception e) {
				logger.error(
						"MessageInterceptor.afterCompletion threw exception", e);
			}
		}
	}
}
