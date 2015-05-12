package com.foxinmy.weixin4j.startup;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.foxinmy.weixin4j.bean.AesToken;
import com.foxinmy.weixin4j.bean.BeanFactory;
import com.foxinmy.weixin4j.dispatcher.WeixinMessageDispatcher;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.interceptor.WeixinMessageInterceptor;
import com.foxinmy.weixin4j.socket.WeixinServerInitializer;

/**
 * 微信netty服务启动程序
 * 
 * @className WeixinServerBootstrap
 * @author jy
 * @date 2014年10月12日
 * @since JDK 1.7
 * @see
 */
public final class WeixinServerBootstrap {

	/**
	 * 默认boss线程数,一般设置为cpu的核数
	 */
	public final static int DEFAULT_BOSSTHREADS = 1;
	/**
	 * 默认worker线程数
	 */
	public final static int DEFAULT_WORKERTHREADS = 20;
	/**
	 * 默认服务启动端口
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
	private final AesToken aesToken;

	/**
	 * 明文模式
	 * 
	 * * @param appid 公众号的唯一ID
	 * 
	 */
	public WeixinServerBootstrap(String token) {
		this(new AesToken(token));
	}

	/**
	 * 兼容模式 & 密文模式
	 * 
	 * @param appid
	 *            公众号的唯一ID
	 * @param token
	 *            开发者填写的token
	 * @param aesKey
	 *            消息加密的密钥
	 */
	public WeixinServerBootstrap(String appid, String token, String aesKey) {
		this(new AesToken(appid, token, aesKey));
	}

	public WeixinServerBootstrap(AesToken aesToken) {
		this.aesToken = aesToken;
		this.messageHandlerList = new LinkedList<WeixinMessageHandler>();
		this.messageInterceptorList = new LinkedList<WeixinMessageInterceptor>();
		this.messageDispatcher = new WeixinMessageDispatcher();
	}

	/**
	 * 默认端口启动服务
	 * 
	 */
	public void startup() throws WeixinException {
		startup(DEFAULT_BOSSTHREADS, DEFAULT_WORKERTHREADS, DEFAULT_SERVERPORT);
	}

	/**
	 * 接受参数启动服务
	 * 
	 * @param bossThreads
	 *            boss线程数,一般设置为cpu的核数
	 * @param workerThreads
	 *            worker线程数
	 * @param serverPort
	 *            服务启动端口
	 * @throws WeixinException
	 */
	public void startup(int bossThreads, int workerThreads, int serverPort)
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
							new WeixinServerInitializer(aesToken,
									messageDispatcher));
			Channel ch = b.bind(serverPort).sync().channel();
			System.err.println("weixin4j server startup OK:" + serverPort);
			ch.closeFuture().sync();
		} catch (WeixinException e) {
			throw e;
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
	 * 将某个消息处理器插入到头部
	 * 
	 * @param messageHandler
	 *            消息处理器
	 * @return
	 */
	public WeixinServerBootstrap insertFirstHandler(
			WeixinMessageHandler messageHandler) {
		messageHandlerList.add(0, messageHandler);
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
	 * 将某个消息拦截器插入到头部
	 * 
	 * @param messageInterceptor
	 *            消息拦截器
	 * @return
	 */
	public WeixinServerBootstrap insertFirstInterceptor(
			WeixinMessageInterceptor messageInterceptor) {
		messageInterceptorList.add(0, messageInterceptor);
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
}