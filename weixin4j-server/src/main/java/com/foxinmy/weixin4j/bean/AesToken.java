package com.foxinmy.weixin4j.bean;

import java.io.Serializable;

/**
 * aes & token
 * 
 * @className AesToken
 * @author jy
 * @date 2015年5月6日
 * @since JDK 1.7
 * @see
 */
public class AesToken implements Serializable {

	private static final long serialVersionUID = -6001008896414323534L;

	/**
	 * 账号ID
	 */
	private String appid;
	/**
	 * 开发者的token
	 */
	private String token;
	/**
	 * 安全模式下的加密密钥
	 */
	private String aesKey;

	public AesToken(String token) {
		this.token = token;
	}

	public AesToken(String appid, String token, String aesKey) {
		this.appid = appid;
		this.token = token;
		this.aesKey = aesKey;
	}

	public String getAppid() {
		return appid;
	}

	public String getToken() {
		return token;
	}

	public String getAesKey() {
		return aesKey;
	}
}
