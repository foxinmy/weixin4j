package com.foxinmy.weixin4j.model;

/**
 * 用户token 一般通过授权页面获得
 * @className UserToken
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.model.AuthResult
 * @see com.foxinmy.weixin4j.model.AuthResult.AuthScope
 */
public class UserToken extends Token {

	private static final long serialVersionUID = 1L;

	private String refresh_token;

	private String scope;

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[UserToken access_token=").append(super.getAccess_token());
		sb.append(", expires_in=").append(super.getExpires_in());
		sb.append(", openid=").append(super.getOpenid());
		sb.append(", refresh_token=").append(refresh_token);
		sb.append(", scope=").append(scope);
		sb.append(", time=").append(super.getTime()).append("]");
		return sb.toString();
	}
}
