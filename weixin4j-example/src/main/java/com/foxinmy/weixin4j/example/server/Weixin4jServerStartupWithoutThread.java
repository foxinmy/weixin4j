package com.foxinmy.weixin4j.example.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * 微信消息服务:单独作为一个服务jar包启动
 *
 * @className Weixin4jServerStartupWithoutThread
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
 */
public class Weixin4jServerStartupWithoutThread {
	/**
	 * 服务监听的端口号,目前微信只支持80端口,可以考虑用nginx做转发到此端口
	 */
	private static int port = 30000;
	/**
	 * 服务器token信息
	 */
	/**
	 * 明文模式:String aesToken = ""; 密文模式:AesToken aesToken = new
	 * AesToken("公众号appid", "公众号token","公众号加密/解密消息的密钥");
	 */
	private static String aesToken = "weixin4j";
	/**
	 * 处理微信消息的全限包名(也可通过addHandler方式一个一个添加)
	 */
	private static String handlerPackage = "com.foxinmy.weixin4j.example.server.handler";

	/**
	 * 入口函数 可使用assembly插件打成可执行zip包:https://github.com/foxinmy/weixin4j/wiki/
	 * assembly%E6%89%93%E5%8C%85
	 *
	 * @param args
	 * @throws WeixinException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws WeixinException {
		// 单独服务启动
		// new WeixinServerBootstrap(aesToken)
		// .handlerPackagesToScan(handlerPackage)
		// .addHandler(DebugMessageHandler.global).openAlwaysResponse()
		// .startup(port);
		// spring容器启动
		ApplicationContext app = new ClassPathXmlApplicationContext(
				new String[] { "classpath:/spring-bean.xml" });
	}
}
