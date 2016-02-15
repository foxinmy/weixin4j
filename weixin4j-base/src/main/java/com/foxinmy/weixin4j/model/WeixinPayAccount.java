package com.foxinmy.weixin4j.model;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 微信支付账户
 * 
 * @className WeixinPayAccount
 * @author jy
 * @date 2015年6月26日
 * @since JDK 1.6
 * @see
 */
public class WeixinPayAccount extends WeixinAccount {

	private static final long serialVersionUID = -2791256176906048632L;
	/**
	 * 公众号支付请求中用于加密的密钥 Key,可验证商户唯一身份,PaySignKey 对应于支付场景中的 appKey 值
	 */
	private String paySignKey;
	/**
	 * 财付通商户身份的标识
	 */
	private String partnerId;
	/**
	 * 财付通商户权限密钥Key
	 */
	private String partnerKey;
	/**
	 * 微信支付分配的商户号(商户平台版)
	 */
	private String mchId;
	/**
	 * 加载支付证书文件的密码(商户平台版)
	 */
	private String certificateKey;
	/**
	 * 微信支付分配的子商户号，受理模式下必填(商户平台版)
	 */
	private String subMchId;
	/**
	 * 微信支付分配的设备号(商户平台版)
	 */
	private String deviceInfo;

	/**
	 * 商户平台版本(V3)字段
	 * 
	 * @param appId
	 *            公众号唯一的身份ID(必填)
	 * @param appSecret
	 *            调用接口的凭证(最好填写)
	 * @param paySignKey
	 *            支付密钥字符串(必填)
	 * @param mchId
	 *            微信支付分配的商户号(必填)
	 */
	public WeixinPayAccount(String appId, String appSecret, String paySignKey,
			String mchId) {
		this(appId, appSecret, paySignKey, mchId, null, null, null, null, null);
	}

	/**
	 * 支付商户信息
	 * 
	 * @param appId
	 *            公众号唯一的身份ID(必填)
	 * @param appSecret
	 *            调用接口的凭证(最好填写)
	 * @param paySignKey
	 *            支付密钥字符串(必填)
	 * @param mchId
	 *            微信支付分配的商户号(V3商户平台版必填)
	 * @param certificateKey
	 *            加载支付证书文件的密码(商户平台版)
	 * @param subMchId
	 *            微信支付分配的子商户号，受理模式下必填(V3商户平台版 非必须)
	 * @param deviceInfo
	 *            微信支付分配的设备号(V3商户平台版 非必须)
	 * @param partnerId
	 *            财付通的商户号(V2版本必填)
	 * @param partnerKey
	 *            财付通商户权限密钥Key(V2版本必填)
	 */
	@JSONCreator
	public WeixinPayAccount(@JSONField(name = "id") String appId,
			@JSONField(name = "secret") String appSecret,
			@JSONField(name = "paySignKey") String paySignKey,
			@JSONField(name = "mchId") String mchId,
			@JSONField(name = "certificateKey") String certificateKey,
			@JSONField(name = "subMchId") String subMchId,
			@JSONField(name = "deviceInfo") String deviceInfo,
			@JSONField(name = "partnerId") String partnerId,
			@JSONField(name = "partnerKey") String partnerKey) {
		super(appId, appSecret);
		this.paySignKey = paySignKey;
		this.mchId = mchId;
		this.certificateKey = certificateKey;
		this.subMchId = subMchId;
		this.deviceInfo = deviceInfo;
		this.partnerId = partnerId;
		this.partnerKey = partnerKey;
	}

	public String getPaySignKey() {
		return paySignKey;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public String getMchId() {
		return mchId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public String getCertificateKey() {
		return StringUtil.isBlank(certificateKey) ? mchId : certificateKey;
	}

	@Override
	public String toString() {
		return "WeixinPayAccount [" + super.toString() + ", paySignKey="
				+ paySignKey + ", partnerId=" + partnerId + ", partnerKey="
				+ partnerKey + ", mchId=" + mchId + ", certificateKey="
				+ getCertificateKey() + ", subMchId=" + subMchId
				+ ", deviceInfo=" + deviceInfo + "]";
	}
}
