package com.foxinmy.weixin4j.api.token;

import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 获取config.properties中的appid&appsecret信息
 * 
 * @className AbstractTokenApi
 * @author jy
 * @date 2014年10月6日
 * @since JDK 1.7
 * @see
 */
public abstract class AbstractTokenApi implements TokenApi {

	protected String getAppid() {
		return ConfigUtil.getValue("app_id");
	}

	protected String getAppsecret() {
		return ConfigUtil.getValue("app_secret");
	}
}
