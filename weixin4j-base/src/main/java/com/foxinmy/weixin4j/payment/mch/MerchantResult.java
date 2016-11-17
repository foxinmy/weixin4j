package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.type.SignType;

/**
 * 调用商户平台接口返回的公用字段
 *
 * @className MerchantResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月21日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MerchantResult extends XmlResult {

	private static final long serialVersionUID = -8430005768959715444L;

	/**
	 * 微信分配的公众账号 ID商户号 非空
	 */
	@XmlElement(name = "appid")
	@JSONField(name = "appid")
	private String appId;
	/**
	 * 微信支付分配的商户号 非空
	 */
	@XmlElement(name = "mch_id")
	@JSONField(name = "mch_id")
	private String mchId;
	/**
	 * 微信分配的子商户公众账号ID 非必须
	 */
	@XmlElement(name = "sub_appid")
	@JSONField(name = "sub_appid")
	private String subAppId;
	/**
	 * 微信支付分配的子商户号 非必须
	 */
	@XmlElement(name = "sub_mch_id")
	@JSONField(name = "sub_mch_id")
	private String subMchId;
	/**
	 * 随机字符串 非空
	 */
	@XmlElement(name = "nonce_str")
	@JSONField(name = "nonce_str")
	private String nonceStr;
	/**
	 * 签名 <font color="red">调用者无需关心</font>
	 */
	private String sign;
	/**
	 * 签名类型 默认MD5
	 */
	@XmlElement(name = "sign_type")
	@JSONField(name = "sign_type")
	private String signType;
	/**
	 * 微信支付分配的终端设备号 可能为空
	 */
	@XmlElement(name = "device_info")
	@JSONField(name = "device_info")
	private String deviceInfo;
	/**
	 * 是否需要继续调用接口 Y- 需要,N-不需要
	 */
	private String recall;

	protected MerchantResult() {
		// jaxb required
	}

	public MerchantResult(String returnCode, String returnMsg) {
		super(returnCode, returnMsg);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getSubAppId() {
		return subAppId;
	}

	public void setSubAppId(String subAppId) {
		this.subAppId = subAppId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	@JSONField(serialize = false)
	public SignType getFormatSignType() {
		return signType != null ? SignType.valueOf(signType.toUpperCase())
				: null;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getRecall() {
		return recall;
	}

	public void setRecall(String recall) {
		this.recall = recall;
	}

	@JSONField(serialize = false)
	public boolean getFormatRecall() {
		return recall != null && recall.equalsIgnoreCase("y");
	}

	@Override
	public String toString() {
		return "appId=" + appId + ", mchId=" + mchId + ", subAppId=" + subAppId
				+ ", subMchId=" + subMchId + ", nonceStr=" + nonceStr
				+ ", sign=" + sign + ", deviceInfo=" + deviceInfo + ", recall="
				+ getFormatRecall() + ", " + super.toString();
	}
}
