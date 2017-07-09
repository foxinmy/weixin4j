package com.foxinmy.weixin4j.example.server;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.DebugMessageHandler;
import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;
import com.foxinmy.weixin4j.util.AesToken;

/**
 * 微信消息服务:单独作为一个服务jar包启动，推荐这种方式去处理微信消息，可在本项目的根目录运行 `mvn package`得到一个可执行的zip包。
 *
 * @className Weixin4jServerStartup
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.6
 */
public final class Weixin4jServerStartup {
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
	 */
	public static void main(String[] args){
		new WeixinServerBootstrap(new AesToken("wxa652fc930afe9b22", "weixin4j", "3XItJRSSkTqH7etjBUbLfLBecLdFfGjbGPfmIHuGftD")) // 指定开发者token信息。
				.handlerPackagesToScan(handlerPackage) // 扫描处理消息的包。
				.addHandler(DebugMessageHandler.global) // 当没有匹配到消息处理时输出调试信息，开发环境打开。
				.openAlwaysResponse() // 当没有匹配到消息处理时输出空白回复(公众号不会出现「该公众号无法提供服务的提示」)，正式环境打开。
				.startup(port); // 绑定服务的端口号，即对外暴露(微信服务器URL地址)的服务端口。
	}
}