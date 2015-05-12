package com.foxinmy.weixin4j.mp.model;

import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 微信公众平台信息
 * 
 * @className WeixinMpAccount
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see <a href=
 *      "https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&token=836970804&lang=zh_CN"
 *      >开发者模式</a>
 */
public class WeixinMpAccount extends WeixinAccount {
	private static final long serialVersionUID = 3689999353867189585L;

	/**
	 * 支付场景下为用户的openid 其余情况可能是公众号的原始ID
	 */
	private String openId;
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
	 * 微信支付分配的子商户号，受理模式下必填(商户平台版)
	 */
	private String subMchId;
	/**
	 * 微信支付分配的设备号(商户平台版)
	 */
	private String deviceInfo;
	/**
	 * 微信支付版本号(如果无则按照mchId来做判断)
	 */
	private int version;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public int getVersion() {
		if (version == 0) {
			return StringUtil.isNotBlank(mchId) ? 3 : 2;
		}
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public WeixinMpAccount() {

	}

	public WeixinMpAccount(String appId, String appSecret) {
		super(appId, appSecret);
	}

	/**
	 * 商户平台版本(V3)字段
	 * 
	 * @param appId
	 *            公众号唯一的身份ID
	 * @param appSecret
	 *            调用接口的凭证
	 * @param paySignKey
	 *            支付密钥字符串
	 * @param mchId
	 *            微信支付分配的商户号
	 */
	public WeixinMpAccount(String appId, String appSecret, String paySignKey,
			String mchId) {
		this(appId, appSecret);
		this.paySignKey = paySignKey;
		this.mchId = mchId;
	}

	/**
	 * V2版本字段
	 * 
	 * @param appId
	 *            公众号唯一的身份ID
	 * @param appSecret
	 *            调用接口的凭证
	 * @param paySignKey
	 *            支付密钥字符串
	 * @param partnerId
	 *            财付通账号的ID
	 * @param partnerKey
	 *            财付通账号的key
	 */
	public WeixinMpAccount(String appId, String appSecret, String paySignKey,
			String partnerId, String partnerKey) {
		this(appId, appSecret);
		this.paySignKey = paySignKey;
		this.partnerId = partnerId;
		this.partnerKey = partnerKey;
	}

	@Override
	public String toString() {
		return "WeixinMpAccount [openId=" + openId + ", paySignKey="
				+ paySignKey + ", partnerId=" + partnerId + ", partnerKey="
				+ partnerKey + ", mchId=" + mchId + ", deviceInfo="
				+ deviceInfo + ", version=" + version + ", " + super.toString()
				+ "]";
	}
}
