package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 微信账户信息
 * 
 * @className WeixinAccount
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see
 */
public class WeixinAccount implements Serializable {
	private static final long serialVersionUID = 3689999353867189585L;

	private String token;
	// 支付场景下为用户的openid 其余情况可能是公众号的原始ID
	private String openId;
	// 公众号身份的唯一标识
	private String appId;
	// 公众平台接口 API 的权限获取所需密钥 Key
	private String appSecret;
	// 公众号支付请求中用于加密的密钥 Key,可验证商户唯一身份,PaySignKey 对应于支付场景中的 appKey 值
	private String paySignKey;
	// 财付通商户身份的标识
	private String partnerId;
	// 财付通商户权限密钥Key
	private String partnerKey;
	// 微信支付商户号V3.x版本
	private String mchId;
	// 微信支付分配的设备号
	private String deviceInfo;
	// 微信支付版本号(如果无则按照mchId来做判断)
	private int version;

	// 是否已经认证
	private boolean isAlive;
	// 是否是服务号
	private boolean isService;
	// 是否是订阅号
	private boolean isSubscribe;

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

	public String getPaySignKey() {
		return paySignKey;
	}

	public void setPaySignKey(String paySignKey) {
		this.paySignKey = paySignKey;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

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

	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public int getVersion() {
		if (version == 0) {
			return StringUtils.isNotBlank(mchId) ? 3 : 2;
		}
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public WeixinAccount() {

	}

	public WeixinAccount(String appId, String appSecret) {
		this.appId = appId;
		this.appSecret = appSecret;
	}

	/**
	 * V3版本字段
	 * 
	 * @param appId
	 * @param appSecret
	 * @param paySignKey
	 * @param mchId
	 */
	public WeixinAccount(String appId, String appSecret, String paySignKey,
			String mchId) {
		this(appId, appSecret);
		this.paySignKey = paySignKey;
		this.mchId = mchId;
	}

	/**
	 * V2版本字段
	 * 
	 * @param appId
	 * @param appSecret
	 * @param paySignKey
	 * @param partnerId
	 * @param partnerKey
	 */
	public WeixinAccount(String appId, String appSecret, String paySignKey,
			String partnerId, String partnerKey) {
		this(appId, appSecret);
		this.paySignKey = paySignKey;
		this.partnerId = partnerId;
		this.partnerKey = partnerKey;
	}

	@Override
	public String toString() {
		return "WeixinAccount [token=" + token + ", openId=" + openId
				+ ", appId=" + appId + ", appSecret=" + appSecret
				+ ", paySignKey=" + paySignKey + ", partnerId=" + partnerId
				+ ", partnerKey=" + partnerKey + ", mchId=" + mchId
				+ ", deviceInfo=" + deviceInfo + ", isAlive=" + isAlive
				+ ", isService=" + isService + ", isSubscribe=" + isSubscribe
				+ "]";
	}
}
