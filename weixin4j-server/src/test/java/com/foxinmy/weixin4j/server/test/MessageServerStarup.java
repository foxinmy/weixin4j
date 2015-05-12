package com.foxinmy.weixin4j.server.test;

import io.netty.channel.ChannelHandlerContext;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.BlankMessageHandler;
import com.foxinmy.weixin4j.handler.DebugMessageHandler;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.MessageInterceptorAdapter;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;

/**
 * 服务启动
 * 
 * @className ServerStarup
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
 */
public class MessageServerStarup {

	final String appid = "wx0d1d598c0c03c999";
	final String token = "carsonliu13450438112";
	final String aesKey = "vlrw8zK2UDSV7Hbmkl8ThzSBmdC11Lw95Um9b2dg3w1";

	/**
	 * 明文模式
	 * 
	 * @throws WeixinException
	 */
	public void test1() throws WeixinException {
		// 所有请求都回复调试的文本消息
		new WeixinServerBootstrap(token).addHandler(DebugMessageHandler.global)
				.startup();
	}

	/**
	 * 密文模式
	 * 
	 * @throws WeixinException
	 */
	public void test2() throws WeixinException {
		// 所有请求都回复调试的文本消息
		new WeixinServerBootstrap(appid, token, aesKey).addHandler(
				DebugMessageHandler.global).startup();
	}

	/**
	 * 针对特定消息回复
	 * 
	 * @throws WeixinException
	 */
	public void test3() throws WeixinException {
		// 回复文本消息
		WeixinMessageHandler messageHandler = new WeixinMessageHandler() {
			@Override
			public WeixinResponse doHandle(WeixinRequest request,
					WeixinMessage message) throws WeixinException {
				return new TextResponse("HelloWorld!");
			}

			@Override
			public boolean canHandle(WeixinRequest request,
					WeixinMessage message) {
				return message.getMsgType().equals("text");
			}
		};
		// 当消息类型为文本(text)时回复「HelloWorld」, 否则回复调试消息
		new WeixinServerBootstrap(appid, token, aesKey).addHandler(
				messageHandler, DebugMessageHandler.global).startup();
	}

	public void test4() throws WeixinException {
		// 扫描包加载消息处理器
		String packageToScan = "com.foxinmy.weixin4j.handler";
		new WeixinServerBootstrap(token).handlerPackagesToScan(packageToScan)
				.startup();
	}

	public void test5() throws WeixinException {
		// 拦截所有请求
		WeixinMessageInterceptor interceptor = new MessageInterceptorAdapter() {
			@Override
			public boolean preHandle(ChannelHandlerContext context,
					WeixinRequest request, WeixinMessage message,
					WeixinMessageHandler handler) throws WeixinException {
				context.write(new TextResponse("所有消息被拦截了！"));
				return false;
			}

			@Override
			public void postHandle(ChannelHandlerContext context,
					WeixinRequest request, WeixinResponse response,
					WeixinMessage message, WeixinMessageHandler handler)
					throws WeixinException {
				System.err.println("preHandle返回为true,执行handler后");
			}

			@Override
			public void afterCompletion(ChannelHandlerContext context,
					WeixinRequest request, WeixinMessage message,
					WeixinMessageHandler handler, WeixinException exception)
					throws WeixinException {
				System.err.println("请求处理完毕");
			}
		};
		new WeixinServerBootstrap(token).addInterceptor(interceptor)
				.addHandler(BlankMessageHandler.global).startup();
	}

	public static void main(String[] args) throws WeixinException {
		new MessageServerStarup().test5();
	}
}
