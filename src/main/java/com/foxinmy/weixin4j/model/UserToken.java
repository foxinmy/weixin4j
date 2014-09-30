package com.foxinmy.weixin4j.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户token 一般通过授权页面获得
 * 
 * @className UserToken
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.model.AuthResult
 * @see com.foxinmy.weixin4j.model.AuthResult.AuthScope
 */
public class UserToken extends Token {

	private static final long serialVersionUID = 1L;

	@JSONField(name = "refresh_token")
	private String refreshToken;

	private String scope;

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
		return "UserToken [refreshToken=" + refreshToken + ", scope=" + scope + ", getAccessToken()=" + getAccessToken() + ", getExpiresIn()=" + getExpiresIn() + ", getOpenid()=" + getOpenid() + ", getTime()=" + getTime() + "]";
	}
}
