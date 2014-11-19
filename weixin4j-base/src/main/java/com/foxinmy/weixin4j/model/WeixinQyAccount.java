package com.foxinmy.weixin4j.model;

/**
 * 微信企业号信息
 * 
 * @className WeixinQyAccount
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see
 */
public class WeixinQyAccount extends WeixinAccount {
	private static final long serialVersionUID = 3689999353867189585L;

	public WeixinQyAccount() {
	}

	public WeixinQyAccount(String corpid, String corpsecret) {
		super(corpid, corpsecret);
	}

	@Override
	public String getTokenUrl() {
		return String.format(Consts.QY_ASSESS_TOKEN_URL, getId(), getSecret());
	}

	@Override
	public String toString() {
		return "WeixinQyAccount [getTokenUrl()=" + getTokenUrl() + ", getId()="
				+ getId() + ", getSecret()=" + getSecret() + ", getToken()="
				+ getToken() + ", getEncodingAesKey()=" + getEncodingAesKey()
				+ "]";
	}
}
