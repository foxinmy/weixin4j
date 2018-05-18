package com.foxinmy.weixin4j.wxa.api;

import java.util.ResourceBundle;

import com.foxinmy.weixin4j.api.BaseApi;

public abstract class WxaApi extends BaseApi {

	private static final ResourceBundle WEIXIN_BUNDLE;

	static {
		WEIXIN_BUNDLE = ResourceBundle
			.getBundle("com/foxinmy/weixin4j/wxa/api/weixin");
	}
	@Override
	protected ResourceBundle weixinBundle() {
		return WEIXIN_BUNDLE;
	}

	protected String getRequestUri(String key, Object... args) {
		return String.format(getRequestUri(key), args);
	}

}
