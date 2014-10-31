package com.foxinmy.weixin4j.model;

import java.io.Serializable;

public class WeixinConfig implements Serializable {

	private static final long serialVersionUID = -3454743453282851243L;

	private String token;
	// 支付场景下为用户的openid 其余情况可能是公众号的原始ID
	private String openId;
	// 公众号身份的唯一标识
	private String appId;
	// 除了支付请求需要用到 paySignKey,公众平台接口 API 的权限获取所需密 钥 Key
	private String appSecret;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public WeixinConfig() {

	}
	public WeixinConfig(String token, String openId, String appId,
			String appSecret) {
		this.token = token;
		this.openId = openId;
		this.appId = appId;
		this.appSecret = appSecret;
	}
	@Override
	public String toString() {
		return "WeixinConfig [token=" + token + ", openId=" + openId
				+ ", appId=" + appId + ", appSecret=" + appSecret + "]";
	}

}
