package com.foxinmy.weixin4j.model;

/**
 * 微信账户信息
 * 
 * @className WeixinAccountV2
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see
 */
public class WeixinAccount extends WeixinAccountV2 {
	private static final long serialVersionUID = 3689999353867189585L;
	// 是否已经认证
	private boolean isAlive;
	// 是否是服务号
	private boolean isService;
	// 是否是订阅号
	private boolean isSubscribe = true;
	// 微信支付商户号
	private String mchId;

	public boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean getIsService() {
		return isService;
	}

	public void setIsService(Boolean isService) {
		this.isService = isService;
	}

	public boolean getIsSubscribe() {
		return isSubscribe;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public WeixinAccount() {
	}

	public WeixinAccount(boolean isAlive, boolean isService,
			boolean isSubscribe, String token, String openId) {
		super.setToken(token);
		super.setOpenId(openId);
		this.isAlive = isAlive;
		this.isService = isService;
		this.isSubscribe = isSubscribe;
	}

	public WeixinAccount(boolean isAlive, boolean isService,
			boolean isSubscribe, String token, String openId, String appId,
			String appSecret) {
		super.setToken(token);
		super.setOpenId(openId);
		super.setAppId(appId);
		super.setAppSecret(appSecret);
		this.isAlive = isAlive;
		this.isService = isService;
		this.isSubscribe = isSubscribe;
	}

	@Override
	public String toString() {
		return "WeixinAccount [isAlive=" + isAlive + ", isService=" + isService
				+ ", isSubscribe=" + isSubscribe + ", mchId=" + mchId
				+ ", getIsAlive()=" + getIsAlive() + ", getIsService()="
				+ getIsService() + ", getIsSubscribe()=" + getIsSubscribe()
				+ ", getMchId()=" + getMchId() + "]";
	}
}
