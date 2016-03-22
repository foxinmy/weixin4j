package com.foxinmy.weixin4j.startup;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foxinmy.weixin4j.dispatcher.BeanFactory;
import com.foxinmy.weixin4j.dispatcher.DefaultMessageMatcher;
import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.dispatcher.WeixinMessageKey;
import com.foxinmy.weixin4j.dispatcher.WeixinMessageMatcher;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.socket.WeixinServerInitializer;
import com.foxinmy.weixin4j.util.AesToken;

/**
 * 微信netty服务启动程序
 * 
 * @className WeixinServerBootstrap
 * @author jy
 * @date 2014年10月12日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.dispatcher.WeixinMessageMatcher
 * @see com.foxinmy.weixin4j.handler.WeixinMessageHandler
 * @see com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor
 * @see com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher
 * @see com.foxinmy.weixin4j.dispatcher.BeanFactory
 */
public final class WeixinServerBootstrap {

	private final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	/**
	 * boss线程数,默认设置为cpu的核数
	 */
	public final static int DEFAULT_BOSSTHREADS;
	/**
	 * worker线程数,默认设置为DEFAULT_BOSSTHREADS * 4
	 */
	public final static int DEFAULT_WORKERTHREADS;
	/**
	 * 服务启动的默认端口
	 */
	public final static int DEFAULT_SERVERPORT = 30000;
	/**
	 * 消息分发器
	 */
	private WeixinMessageDispatcher messageDispatcher;
	/**
	 * 消息处理器
	 */
	private List<WeixinMessageHandler> messageHandlerList;
	/**
	 * 消息拦截器
	 */
	private List<WeixinMessageInterceptor> messageInterceptorList;

	/**
	 * aes and token
	 * 
	 */
	private final Map<String, AesToken> aesTokenMap;

	static {
		DEFAULT_BOSSTHREADS = Runtime.getRuntime().availableProcessors();
		DEFAULT_WORKERTHREADS = DEFAULT_BOSSTHREADS * 4;
	}

	/**
	 * 
	 * 明文模式
	 * 
	 * @param token
	 *            开发者token
	 * 
	 */
	public WeixinServerBootstrap(String token) {
		this(null, token, null);
	}

	/**
	 * 明文模式 & 兼容模式 & 密文模式
	 * 
	 * @param weixinId
	 *            公众号的应用ID(appid/corpid) 密文&兼容模式下需要填写
	 * 
	 * @param token
	 *            开发者填写的token 无论哪种模式都需要填写
	 * @param aesKey
	 *            消息加密的密钥 密文&兼容模式下需要填写
	 */
	public WeixinServerBootstrap(String weixinId, String token, String aesKey) {
		this(new AesToken(weixinId, token, aesKey));
	}

	/**
	 * 多个公众号的支持
	 * <p>
	 * <font color="red">请注意：需在服务接收事件的URL中附加一个名为wexin_id的参数,其值请填写公众号的appid/
	 * corpid</font>
	 * <p>
	 * 
	 * @param aesTokens
	 *            多个公众号
	 * @return
	 */
	public WeixinServerBootstrap(AesToken... aesToken) {
		this(new DefaultMessageMatcher(), aesToken);
	}

	/**
	 * 多个公众号的支持
	 * <p>
	 * <font color="red">请注意：需在服务接收事件的URL中附加一个名为wexin_id的参数,其值请填写公众号的appid/
	 * corpid</font>
	 * <p>
	 * 
	 * @param messageMatcher
	 *            消息匹配器
	 * @param aesTokens
	 *            公众号信息
	 * @return
	 */
	public WeixinServerBootstrap(WeixinMessageMatcher messageMatcher,
			AesToken... aesTokens) {
		if (messageMatcher == null) {
			throw new IllegalArgumentException("MessageMatcher not be null");
		}
		if (aesTokens == null) {
			throw new IllegalArgumentException("AesToken not be null");
		}
		this.aesTokenMap = new HashMap<String, AesToken>();
		for (AesToken aesToken : aesTokens) {
			this.aesTokenMap.put(aesToken.getWeixinId(), aesToken);
		}
		this.aesTokenMap.put(null, aesTokens[0]);
		this.messageHandlerList = new ArrayList<WeixinMessageHandler>();
		this.messageInterceptorList = new ArrayList<WeixinMessageInterceptor>();
		this.messageDispatcher = new WeixinMessageDispatcher(messageMatcher);
	}

	/**
	 * 默认端口启动服务
	 * 
	 */
	public void startup() throws WeixinException {
		startup(DEFAULT_SERVERPORT);
	}

	/**
	 * 指定端口启动服务
	 * 
	 */
	public void startup(int serverPort) throws WeixinException {
		startup(DEFAULT_BOSSTHREADS, DEFAULT_WORKERTHREADS, serverPort);
	}

	/**
	 * 接受参数启动服务
	 * 
	 * @param bossThreads
	 *            boss线程数
	 * @param workerThreads
	 *            worker线程数
	 * @param serverPort
	 *            服务启动端口
	 * @return
	 * @throws WeixinException
	 */
	public void startup(int bossThreads, int workerThreads, final int serverPort)
			throws WeixinException {
		messageDispatcher.setMessageHandlerList(messageHandlerList);
		messageDispatcher.setMessageInterceptorList(messageInterceptorList);

		EventLoopGroup bossGroup = new NioEventLoopGroup(bossThreads);
		EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreads);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler())
					.childHandler(
							new WeixinServerInitializer(aesTokenMap,
									messageDispatcher));
			Channel ch = b.bind(serverPort)
					.addListener(new FutureListener<Void>() {
						@Override
						public void operationComplete(Future<Void> future)
								throws Exception {
							if (future.isSuccess()) {
								logger.info("weixin4j server startup OK:{}",
										serverPort);
							} else {
								logger.info("weixin4j server startup FAIL:{}",
										serverPort);
							}
						}
					}).sync().channel();
			ch.closeFuture().sync();
		} catch (InterruptedException e) {
			throw new WeixinException(e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	/**
	 * 添加一个或者多个消息处理器
	 * 
	 * @param messageHandler
	 *            消息处理器
	 * @return
	 */
	public WeixinServerBootstrap addHandler(
			WeixinMessageHandler... messageHandler) {
		messageHandlerList.addAll(Arrays.asList(messageHandler));
		return this;
	}

	/**
	 * 插入一个或多个消息拦截器
	 * 
	 * @param messageInterceptor
	 *            消息拦截器
	 * @return
	 */
	public WeixinServerBootstrap addInterceptor(
			WeixinMessageInterceptor... messageInterceptor) {
		messageInterceptorList.addAll(Arrays.asList(messageInterceptor));
		return this;
	}

	/**
	 * 按照包名去添加消息处理器
	 * 
	 * @param messageHandlerPackages
	 *            消息处理器所在的包名
	 * @return
	 */
	public WeixinServerBootstrap handlerPackagesToScan(
			String... messageHandlerPackages) {
		messageDispatcher.setMessageHandlerPackages(messageHandlerPackages);
		return this;
	}

	/**
	 * 按照包名去添加消息拦截器
	 * 
	 * @param messageInterceptorPackages
	 *            消息拦截器所在的包名
	 * @return
	 */
	public WeixinServerBootstrap interceptorPackagesToScan(
			String... messageInterceptorPackages) {
		messageDispatcher
				.setMessageInterceptorPackages(messageInterceptorPackages);
		return this;
	}

	/**
	 * 声明处理器跟拦截器类实例化的构造工厂,否则通过Class.newInstance的方式构造
	 * 
	 * @param beanFactory
	 *            Bean构造工厂
	 * @return
	 */
	public WeixinServerBootstrap resolveBeanFactory(BeanFactory beanFactory) {
		messageDispatcher.setBeanFactory(beanFactory);
		return this;
	}

	/**
	 * 注册消息类型
	 * 
	 * @param messageKey
	 *            消息key
	 * @param messageClass
	 *            消息类
	 * @return
	 */
	public WeixinServerBootstrap registMessageClass(
			WeixinMessageKey messageKey,
			Class<? extends WeixinMessage> messageClass) {
		messageDispatcher.registMessageClass(messageKey, messageClass);
		return this;
	}

	/**
	 * 打开总是响应开关,如未匹配到MessageHandler时回复空白消息
	 */
	public WeixinServerBootstrap openAlwaysResponse() {
		messageDispatcher.openAlwaysResponse();
		return this;
	}

	public final static String VERSION = "1.1.6";
}