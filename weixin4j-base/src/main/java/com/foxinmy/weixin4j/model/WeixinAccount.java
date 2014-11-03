package com.foxinmy.weixin4j.model;

/**
 * 微信账户信息
 * 
 * @className WeixinAccount
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see
 */
public class WeixinAccount extends WeixinConfig {
	private static final long serialVersionUID = 3689999353867189585L;

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

	// 是否已经认证
	private boolean isAlive;
	// 是否是服务号
	private boolean isService;
	// 是否是订阅号
	private boolean isSubscribe;

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

	public WeixinAccount() {

	}

	public WeixinAccount(String appId, String appSecret, String paySignKey,
			String mchId) {
		super(appId, appSecret);
		this.paySignKey = paySignKey;
		this.mchId = mchId;
	}

	public WeixinAccount(String appId, String appSecret, String paySignKey,
			String partnerId, String partnerKey) {
		super(appId, appSecret);
		this.paySignKey = paySignKey;
		this.partnerId = partnerId;
		this.partnerKey = partnerKey;
	}

}
