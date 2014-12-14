package com.foxinmy.weixin4j.mp.payment;

import com.foxinmy.weixin4j.http.XmlResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 调用V3.x接口返回的公用字段
 * 
 * @className ApiResult
 * @author jy
 * @date 2014年10月21日
 * @since JDK 1.7
 * @see
 */
public class ApiResult extends XmlResult {

	private static final long serialVersionUID = -8430005768959715444L;

	@XStreamAlias("appid")
	private String appId;// 微信分配的公众账号 ID商户号 非空
	@XStreamAlias("mch_id")
	private String mchId;// 微信支付分配的商户号 非空
	@XStreamAlias("sub_mch_id")
	private String subMchId; // 未知 可能为空
	@XStreamAlias("nonce_str")
	private String nonceStr;// 随机字符串 非空
	private String sign;// 签名 非空
	@XStreamAlias("device_info")
	private String deviceInfo;// 微信支付分配的终端设备号 可能为空
	private String recall;// 是否需要继续调用接口 Y- 需要,N-不需要

	public ApiResult() {

	}

	public ApiResult(String returnCode, String returnMsg) {
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

	@Override
	public String toString() {
		return "appId=" + appId + ", mchId=" + mchId + ", subMchId=" + subMchId
				+ ", nonceStr=" + nonceStr + ", sign=" + sign + ", deviceInfo="
				+ deviceInfo + ", recall=" + recall + ", " + super.toString();
	}
}
