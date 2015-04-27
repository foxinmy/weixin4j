package com.foxinmy.weixin4j.mp.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.Token;

/**
 * 用户授权token 一般通过授权页面获得
 * 
 * @className OauthToken
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.model.AuthResult
 * @see com.foxinmy.weixin4j.mp.model.AuthResult.AuthScope
 */
public class OauthToken extends Token {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户的openi
	 */
	private String openid;

	/**
	 * 刷新token时的凭证
	 */
	@JSONField(name = "refresh_token")
	private String refreshToken;

	private String scope;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "OauthToken [openid=" + openid + ", refreshToken="
				+ refreshToken + ", scope=" + scope + ", getAccessToken()="
				+ getAccessToken() + ", getExpiresIn()=" + getExpiresIn()
				+ ", getTime()=" + getTime() + "]";
	}
}
