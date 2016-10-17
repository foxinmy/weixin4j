package com.foxinmy.weixin4j.example.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.DebugMessageHandler;
import com.foxinmy.weixin4j.spring.SpringBeanFactory;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;
import com.foxinmy.weixin4j.util.AesToken;

import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 微信消息服务:需要另外开启一个线程去启动服务,这里值得注意的时：weixin4j-serve本身是作为一个单独的服务来启动的，可以不依赖Spring容器，
 * 但考虑到目前都是Spring mvc的架构，这里就需要使用一个独立的线程去启动服务，其实本身没有使用spring mvc的API，
 * 以后会考虑支持servlet api去集成不同的web框架。
 * 
 * @className Weixin4jServerStartupWithThread
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.6
 */
public class Weixin4jServerStartupWithThread implements ApplicationContextAware {
	/**
	 * 服务监听的端口号,目前微信只支持80端口,可以考虑用nginx做转发到此端口
	 */
	private final int port;
	/**
	 * 服务器token信息
	 */
	/**
	 * 明文模式:String aesToken = ""; 密文模式:AesToken aesToken = new
	 * AesToken("公众号appid", "公众号token","公众号加密/解密消息的密钥");
	 */
	private final AesToken aesToken;
	/**
	 * 处理微信消息的全限包名(也可通过addHandler方式一个一个添加)
	 */
	private final String handlerPackage;
	/**
	 * 用spring去获取bean
	 */
	private ApplicationContext applicationContext;

	private Weixin4jServerStartupWithThread(int port, AesToken aesToken,
			String handlerPackage) {
		this.port = port;
		this.aesToken = aesToken;
		this.handlerPackage = handlerPackage;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private ExecutorService executor;

	/**
	 * 启动函数
	 * 
	 * @throws WeixinException
	 */
	public void start() {
		executor = Executors.newCachedThreadPool();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					new WeixinServerBootstrap(aesToken) // 指定开发者token信息。
							.handlerPackagesToScan(handlerPackage) // 扫描处理消息的包。
							.resolveBeanFactory(
									new SpringBeanFactory(applicationContext)) // 声明处理消息类由Spring容器去实例化。
							.addHandler(DebugMessageHandler.global) // 当没有匹配到消息处理时输出调试信息，开发环境打开。
							.openAlwaysResponse() // 当没有匹配到消息处理时输出空白回复(公众号不会出现「该公众号无法提供服务的提示」)，正式环境打开。
							.startup(port); // 绑定服务的端口号，即对外暴露(微信服务器URL地址)的服务端口。
				} catch (WeixinException e) {
					InternalLoggerFactory.getInstance(getClass()).error(
							"weixin4j server startup:FAIL", e);
				}
			}
		});
	}

	public void stop() {
		executor.shutdown();
	}
}
