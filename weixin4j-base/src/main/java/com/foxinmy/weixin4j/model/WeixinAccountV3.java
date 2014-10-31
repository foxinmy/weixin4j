package com.foxinmy.weixin4j.model;

/**
 * 微信支付V3.3.6
 * 
 * @className WeixinConfig
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see
 */
public class WeixinAccountV3 extends WeixinConfig {
	private static final long serialVersionUID = 3689999353867189585L;
	// 公众号支付请求中用于加密的密钥 Key,可验证商户唯一身份,PaySignKey 对应于支付场景中的 appKey 值
	private String paySignKey;
	// 微信支付商户号
	private String mchId;

	public String getPaySignKey() {
		return paySignKey;
	}
	public void setPaySignKey(String paySignKey) {
		this.paySignKey = paySignKey;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public WeixinAccountV3() {
	}

	public WeixinAccountV3(String appId, String appSecret, String paySignKey,
			String mchId, String openId) {
		super(null, openId, appId, appSecret);
		this.mchId = mchId;
		this.paySignKey = paySignKey;
	}
	@Override
	public String toString() {
		return "WeixinAccountV3 [paySignKey=" + paySignKey + ", mchId=" + mchId
				+ ", getPaySignKey()=" + getPaySignKey() + ", getMchId()="
				+ getMchId() + "]";
	}
}
