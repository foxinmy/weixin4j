package com.foxinmy.weixin4j.example.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.spring.SpringBeanFactory;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;
import com.foxinmy.weixin4j.util.AesToken;

import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * 微信消息服务:需要另外开启一个线程去启动服务,比如在spring mvc中
 * 
 * @className Weixin4jServerStartupWithThread
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
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
					new WeixinServerBootstrap(aesToken)
							.handlerPackagesToScan(handlerPackage)
							.resolveBeanFactory(
									new SpringBeanFactory(applicationContext))
							.openAlwaysResponse().startup(port);
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
