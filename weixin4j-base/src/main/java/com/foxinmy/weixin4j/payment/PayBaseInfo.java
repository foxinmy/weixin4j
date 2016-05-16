package com.foxinmy.weixin4j.payment;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.SignType;

/**
 * 基本信息
 * 
 * @className PayBaseInfo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月5日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayBaseInfo implements Serializable {

	private static final long serialVersionUID = 1843024880782466990L;

	/**
	 * 公众号ID
	 */
	@JSONField(name = "appId")
	@XmlElement(name = "AppId")
	private String appId;
	/**
	 * 时间戳
	 */
	@JSONField(name = "timeStamp")
	@XmlElement(name = "TimeStamp")
	private String timeStamp;
	/**
	 * 随机字符串
	 */
	@JSONField(name = "nonceStr")
	@XmlElement(name = "NonceStr")
	private String nonceStr;
	/**
	 * 签名结果
	 */
	@JSONField(name = "paySign")
	@XmlElement(name = "AppSignature")
	private String paySign;
	/**
	 * 签名方式
	 */
	@JSONField(name = "signType")
	@XmlElement(name = "SignMethod")
	private String signType;

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

	@XmlTransient
	@JSONField(serialize = false)
	public SignType getFormatSignType() {
		return signType != null ? SignType.valueOf(signType.toUpperCase())
				: null;
	}

	public void setSignType(SignType signType) {
		this.signType = signType != null ? signType.name() : null;
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
		return "appId=" + appId + ", timeStamp=" + timeStamp + ", nonceStr="
				+ nonceStr + ", paySign=" + paySign + ", signType=" + signType;
	}
}
