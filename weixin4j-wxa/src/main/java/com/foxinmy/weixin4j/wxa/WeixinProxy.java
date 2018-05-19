package com.foxinmy.weixin4j.wxa;

import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.wxa.api.LoginApi;

/**
 * @since 1.8
 */
public class WeixinProxy {

	private final LoginApi loginApi;

	public WeixinProxy(WeixinAccount weixinAccount) {
		if (weixinAccount == null) {
			throw new IllegalArgumentException(
				"weixinAccount must not be empty");
		}

		this.loginApi = new LoginApi(weixinAccount);
	}

	public LoginApi getLoginApi() {
		return loginApi;
	}

}
