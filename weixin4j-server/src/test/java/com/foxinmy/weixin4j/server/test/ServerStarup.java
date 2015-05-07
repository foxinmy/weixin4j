package com.foxinmy.weixin4j.server.test;

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
public class ServerStarup {
	public static void main(String[] args) {
		String appid = "";
		String token = "";
		String aesKey = "";
		WeixinServerBootstrap.startup(appid, token, aesKey);
		//WeixinServerBootstrap.startup(token);
	}
}
