package com.foxinmy.weixin4j.mp.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.Token;

/**
 * 用户授权token 一般通过授权页面获得
 *
 * @className OauthToken
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 */
public class OauthToken extends Token {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户的openid
	 */
	@JSONField(name = "openid")
	private String openId;
	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	@JSONField(name = "unionid")
	private String unionId;

	/**
	 * 刷新token时的凭证
	 */
	@JSONField(name = "refresh_token")
	private String refreshToken;

	private String scope;

	public OauthToken(String accessToken, long expires) {
		super(accessToken, expires);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
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
		return "OauthToken [openId=" + openId + ", unionId=" + unionId
				+ ", refreshToken=" + refreshToken + ", scope=" + scope + ", "
				+ super.toString() + "]";
	}
}
