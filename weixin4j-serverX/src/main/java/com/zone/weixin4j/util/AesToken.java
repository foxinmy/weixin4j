package com.zone.weixin4j.util;

import java.io.Serializable;

/**
 * aes & token
 *
 * @className AesToken
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月6日
 * @since JDK 1.6
 * @see
 */
public class AesToken implements Serializable {

	private static final long serialVersionUID = -6001008896414323534L;

	/**
	 * 账号ID(原始id/appid/corpid)
	 */
	private String weixinId;
	/**
	 * 开发者的token
	 */
	private String token;
	/**
	 * 安全模式下的加密密钥
	 */
	private String aesKey;

	/**
	 * 一般为明文模式
	 *
	 * @param token
	 *            开发者的Token
	 */
	public AesToken(String token) {
		this(null, token, null);
	}

	/**
	 * 一般为AES加密模式
	 *
	 * @param weixinId
	 *            公众号的应用ID(原始id/appid/corpid)
	 * @param token
	 *            开发者Token
	 * @param aesKey
	 *            解密的EncodingAESKey
	 */
	public AesToken(String weixinId, String token, String aesKey) {
		this.weixinId = weixinId;
		this.token = token;
		this.aesKey = aesKey;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public String getToken() {
		return token;
	}

	public String getAesKey() {
		return aesKey;
	}

	@Override
	public String toString() {
		return "AesToken [weixinId=" + weixinId + ", token=" + token
				+ ", aesKey=" + aesKey + "]";
	}
}
