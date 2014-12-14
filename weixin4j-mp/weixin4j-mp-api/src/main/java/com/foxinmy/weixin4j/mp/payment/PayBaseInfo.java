package com.foxinmy.weixin4j.mp.payment;

import java.io.Serializable;

import com.foxinmy.weixin4j.mp.type.SignType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 基本信息
 * 
 * @className PayBaseInfo
 * @author jy
 * @date 2014年11月5日
 * @since JDK 1.7
 * @see
 */
public class PayBaseInfo implements Serializable {

	private static final long serialVersionUID = 1843024880782466990L;
	@XStreamAlias("AppId")
	private String appId; // 公众号ID

	@XStreamAlias("TimeStamp")
	private String timeStamp; // 时间戳

	@XStreamAlias("NonceStr")
	private String nonceStr; // 随机字符串

	@XStreamAlias("AppSignature")
	private String paySign; // 签名结果

	@XStreamAlias("SignMethod")
	private String signType; // 签名方式

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(SignType signType) {
		if (signType != null) {
			this.signType = signType.name();
		} else {
			this.signType = null;
		}
	}

	public PayBaseInfo() {
	}

	public PayBaseInfo(String appId, String timestamp, String noncestr) {
		this.appId = appId;
		this.timeStamp = timestamp;
		this.nonceStr = noncestr;
	}

	@Override
	public String toString() {
		return "appId=" + appId + ", timeStamp=" + timeStamp
				+ ", nonceStr=" + nonceStr + ", paySign=" + paySign
				+ ", signType=" + signType;
	}
}
