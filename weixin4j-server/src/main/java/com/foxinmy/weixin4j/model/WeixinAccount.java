package com.foxinmy.weixin4j.model;

import java.io.Serializable;

/**
 * 微信账号信息
 * 
 * @className WeixinAccount
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see
 */
public class WeixinAccount implements Serializable {
	private static final long serialVersionUID = -6001008896414323534L;

	/**
	 * 唯一的身份标识
	 */
	private String id;
	/**
	 * 调用接口的密钥
	 */
	private String secret;
	private String token;
	/**
	 * 安全模式下的加密密钥
	 */
	private String encodingAesKey;

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

	@Override
	public String toString() {
		return "id=" + id + ", secret=" + secret + ", token=" + token
				+ ", encodingAesKey=" + encodingAesKey;
	}
}
