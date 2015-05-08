package com.foxinmy.weixin4j.interceptor;

import io.netty.channel.ChannelHandlerContext;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 微信消息拦截器
 * 
 * @className WeixinMessageInterceptor
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
 */
public interface WeixinMessageInterceptor {

	/**
	 * 执行handler前
	 * 
	 * @param ctx
	 *            通道环境
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @param handler
	 *            消息处理器
	 * @return
	 * @throws WeixinException
	 */
	boolean preHandle(ChannelHandlerContext ctx, WeixinRequest request,
			WeixinMessage message, WeixinMessageHandler handler)
			throws WeixinException;

	/**
	 * 执行handler后
	 * 
	 * @param ctx
	 *            通道环境
	 * @param request
	 *            微信请求
	 * @param response
	 *            微信响应
	 * @param message
	 *            微信消息
	 * @param handler
	 *            消息处理器
	 * @throws WeixinException
	 */
	void postHandle(ChannelHandlerContext ctx, WeixinRequest request,
			WeixinResponse response, WeixinMessage message,
			WeixinMessageHandler handler) throws WeixinException;

	/**
	 * 全部执行后
	 * 
	 * @param ctx
	 *            通道环境
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @param handler
	 *            消息处理器
	 * @param exception
	 *            执行异常
	 * @throws WeixinException
	 */
	void afterCompletion(ChannelHandlerContext ctx, WeixinRequest request,
			WeixinMessage message, WeixinMessageHandler handler,
			WeixinException exception) throws WeixinException;
}
