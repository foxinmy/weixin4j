package com.foxinmy.weixin4j.server.test;

import java.util.Set;

import io.netty.channel.ChannelHandlerContext;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.DebugMessageHandler;
import com.foxinmy.weixin4j.handler.MessageHandlerAdapter;
import com.foxinmy.weixin4j.handler.MultipleMessageHandlerAdapter;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.message.TextMessage;
import com.foxinmy.weixin4j.mp.event.ScanEventMessage;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;

/**
 * 服务启动测试类
 * 
 * @className MessageServerStartup
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.6
 * @see
 */
public class MessageServerStartup {

	// 公众号ID
	final String weixinId = "wx4ab8f8de58159a57";
	// 开发者token
	final String token = "weixin4j";
	// AES密钥(安全模式)
	final String aesKey = "";

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
		new WeixinServerBootstrap(weixinId, token, aesKey).addHandler(
				DebugMessageHandler.global).startup();
	}

	/**
	 * 针对特定消息回复
	 * 
	 * @throws WeixinException
	 */
	public void test3() throws WeixinException {
		// 针对文本消息回复
		WeixinMessageHandler messageHandler = new MessageHandlerAdapter<TextMessage>() {
			@Override
			public WeixinResponse doHandle0(WeixinRequest request,
					TextMessage message) throws WeixinException {
				return new TextResponse("HelloWorld!");
			}
		};
		// 当消息类型为文本(text)时回复「HelloWorld」, 否则回复调试消息
		new WeixinServerBootstrap(weixinId, token, aesKey).addHandler(
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
		WeixinMessageInterceptor interceptor = new WeixinMessageInterceptor() {
			@Override
			public boolean preHandle(ChannelHandlerContext context,
					WeixinRequest request, WeixinMessage message,
					WeixinMessageHandler handler) throws WeixinException {
				context.writeAndFlush(new TextResponse("所有消息被拦截了！"));
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
					WeixinRequest request, WeixinResponse response,
					WeixinMessage message, WeixinMessageHandler handler,
					Exception exception) throws WeixinException {
				System.err.println("请求处理完毕");
			}

			@Override
			public int weight() {
				return 0;
			}
		};
		new WeixinServerBootstrap(token).addInterceptor(interceptor)
				.openAlwaysResponse().startup();
	}

	@SuppressWarnings("unchecked")
	public void test6() throws WeixinException {
		MultipleMessageHandlerAdapter messageHandler = new MultipleMessageHandlerAdapter(
				ScanEventMessage.class, TextMessage.class) {
			@Override
			public WeixinResponse doHandle(WeixinRequest request,
					WeixinMessage message, Set<String> nodeNames)
					throws WeixinException {
				return new TextResponse("处理了扫描和文字消息");
			}
		};
		new WeixinServerBootstrap(token).addHandler(messageHandler,
				DebugMessageHandler.global).startup();
	}

	public static void main(String[] args) throws Exception {
		new MessageServerStartup().test6();
	}
}
