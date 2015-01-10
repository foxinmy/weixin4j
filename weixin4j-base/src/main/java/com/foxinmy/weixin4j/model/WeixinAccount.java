package com.foxinmy.weixin4j.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.type.AccountType;

/**
 * 微信账号信息
 * 
 * @className WeixinAccount
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see
 */
public abstract class WeixinAccount implements Serializable {
	private static final long serialVersionUID = -6001008896414323534L;

	// 唯一的身份标识
	private String id;
	// 调用接口的密钥
	private String secret;
	private String token;
	// 安全模式下的加密密钥
	private String encodingAesKey;

	public abstract AccountType getAccountType();

	public WeixinAccount() {
	}

	public WeixinAccount(String id, String secret) {
		this.id = id;
		this.secret = secret;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAesKey() {
		return encodingAesKey;
	}

	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}

	/**
	 * 拼接授权URL
	 * 
	 * @param redirectUri
	 *            授权后重定向的回调链接地址
	 * @param scope
	 *            应用授权作用域，snsapi_base
	 *            （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
	 *            （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @param state
	 *            重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值
	 * @return 授权URL
	 */
	public String getOauthAuthorizeUrl(String redirectUri, String scope,
			String state) {
		if (StringUtils.isBlank(scope)) {
			scope = "snsapi_base";
		}
		if (StringUtils.isBlank(state)) {
			state = "STATE";
		}
		try {
			return String.format(
					Consts.OAUTH_AUTHORIZE_URL,
					URLEncoder.encode(redirectUri,
							org.apache.http.Consts.UTF_8.name()), id, scope,
					state);
		} catch (UnsupportedEncodingException ignore) {
			;
		}
		return "";
	}

	@Override
	public String toString() {
		return "id=" + id + ", secret=" + secret + ", token=" + token
				+ ", encodingAesKey=" + encodingAesKey;
	}
}
