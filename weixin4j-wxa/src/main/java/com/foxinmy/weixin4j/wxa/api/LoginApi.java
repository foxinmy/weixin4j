package com.foxinmy.weixin4j.wxa.api;

import java.util.Properties;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.wxa.model.Session;

/**
 * 登录凭证校验。
 *
 * @since 1.8
 */
public class LoginApi extends WxaApi {

	private final WeixinAccount weixinAccount;

	public LoginApi(WeixinAccount weixinAccount) {
		this(weixinAccount, null);
	}

	public LoginApi(WeixinAccount weixinAccount, Properties properties) {
		super(properties);
		this.weixinAccount = weixinAccount;
	}

	public Session jscode2session(String jsCode) throws WeixinException {
		return jscode2session(jsCode, "authorization_code");
	}

	/**
	 * 登录凭证校验
	 *
	 * @param jsCode 登录时获取的 code
	 * @param grantType 填写为 authorization_code
	 * @return the session.
	 * @throws WeixinException 发生错误时。比如 <code>js_code</code> 无效。
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/wx.login.html">登录凭证校验<a/>
	 */
	public Session jscode2session(String jsCode, String grantType) throws WeixinException {
		String jscode2sessionUri = getRequestUri("sns_jscode2session",
			weixinAccount.getId(), weixinAccount.getSecret(), jsCode, grantType);
		WeixinResponse response = weixinExecutor.get(jscode2sessionUri);
		return response.getAsObject(new TypeReference<Session>() {
		});
	}

}
