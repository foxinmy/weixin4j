package com.foxinmy.weixin4j.model;

/**
 * 微信支付V2.7
 * 
 * @className WeixinAccountV2
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see
 */
public class WeixinAccountV2 extends WeixinConfig {
	private static final long serialVersionUID = 3689999353867189585L;
	// 公众号支付请求中用于加密的密钥 Key,可验证商户唯一身份,PaySignKey 对应于支付场景中的 appKey 值
	private String paySignKey;
	// 财付通商户身份的标识
	private String partnerId;
	// 财付通商户权限密钥Key
	private String partnerKey;

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

	public WeixinAccountV2() {
	}
	public WeixinAccountV2(String openId, String appId, String appSecret,
			String paySignKey, String partnerId, String partnerKey) {
		super(null, openId, appId, appSecret);
		this.paySignKey = paySignKey;
		this.partnerId = partnerId;
		this.partnerKey = partnerKey;
	}
	@Override
	public String toString() {
		return "WeixinAccountV2 [paySignKey=" + paySignKey + ", partnerId="
				+ partnerId + ", partnerKey=" + partnerKey
				+ ", getPaySignKey()=" + getPaySignKey() + ", getPartnerId()="
				+ getPartnerId() + ", getPartnerKey()=" + getPartnerKey() + "]";
	}
}
