package com.foxinmy.weixin4j.model;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 微信支付账户
 *
 * @className WeixinPayAccount
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月26日
 * @since JDK 1.6
 * @see
 */
public class WeixinPayAccount extends WeixinAccount {

	private static final long serialVersionUID = -2791256176906048632L;
	/**
	 * 公众号支付请求中用于加密的密钥
	 */
	private String paySignKey;
	/**
	 * 微信支付分配的商户号
	 */
	private String mchId;
	/**
	 * 加载支付证书文件的密码(默认为商户号)
	 */
	private String certificateKey;
	/**
	 * 微信支付分配的设备号
	 */
	private String deviceInfo;

	/**
	 * 财付通商户身份的标识
	 */
	private String partnerId;

	/**
	 * 微信分配的子商户公众账号ID
	 */
	private String subId;
	/**
	 * 微信支付分配的子商户号
	 */
	private String subMchId;

	/**
	 * 支付商户信息
	 *
	 * @param id
	 *            公众号唯一的身份ID(必填)
	 * @param paySignKey
	 *            支付密钥字符串(必填)
	 * @param mchId
	 *            微信支付分配的商户号(必填)
	 */
	public WeixinPayAccount(String id, String paySignKey, String mchId) {
		this(id, null, paySignKey, mchId, null, null, null, null, null);
	}

	/**
	 * 支付商户信息
	 *
	 * @param id
	 *            公众号唯一的身份ID(必填)
	 * @param secret
	 *            公众号调用接口的凭证(最好填写)
	 * @param paySignKey
	 *            支付密钥字符串(必填)
	 * @param mchId
	 *            微信支付分配的商户号(必填)
	 * @param certificateKey
	 *            加载支付证书文件的密码(默认为商户号)
	 * @param deviceInfo
	 *            微信支付分配的设备号(非必填)
	 * @param partnerId
	 *            财付通的商户号(非必填)
	 * @param subId
	 *            微信分配的子商户公众账号ID(非必填)
	 * @param subMchId
	 *            微信支付分配的子商户号(非必填)
	 */
	@JSONCreator
	public WeixinPayAccount(@JSONField(name = "id") String id,
			@JSONField(name = "secret") String secret,
			@JSONField(name = "paySignKey") String paySignKey,
			@JSONField(name = "mchId") String mchId,
			@JSONField(name = "certificateKey") String certificateKey,
			@JSONField(name = "deviceInfo") String deviceInfo,
			@JSONField(name = "partnerId") String partnerId,
			@JSONField(name = "subId") String subId,
			@JSONField(name = "subMchId") String subMchId) {
		super(id, secret);
		this.paySignKey = paySignKey;
		this.mchId = mchId;
		this.certificateKey = certificateKey;
		this.deviceInfo = deviceInfo;
		this.partnerId = partnerId;
		this.subId = subId;
		this.subMchId = subMchId;
	}

	public String getPaySignKey() {
		return paySignKey;
	}

	public String getMchId() {
		return mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public String getCertificateKey() {
		return StringUtil.isBlank(certificateKey) ? mchId : certificateKey;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getSubId() {
		return subId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	@Override
	public String toString() {
		return "WeixinPayAccount [" + super.toString() + ", paySignKey="
				+ paySignKey + ", mchId=" + mchId + ", certificateKey="
				+ certificateKey + ", deviceInfo=" + deviceInfo
				+ ", partnerId=" + partnerId + ", subId=" + subId
				+ ", subMchId=" + subMchId + "]";
	}
}
