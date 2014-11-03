package com.foxinmy.weixin4j.mp.payment;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PayRequest implements Serializable {

	private static final long serialVersionUID = -453746488398523883L;

	// 公众号ID
	@XStreamAlias("AppId")
	private String appId;
	// 当前时间戳
	@XStreamAlias("TimeStamp")
	private String timeStamp;
	// 随机字符串
	@XStreamAlias("NonceStr")
	private String nonceStr;
	// 订单详情扩展 订单信息组成该字符串
	@XStreamAlias("Package")
	private String packageInfo;
	// 签名方式 数取值"SHA1"
	@XStreamAlias("SignMethod")
	private String signType;
	// 商户将接口列表中的参数按照指定方式进行 签名,签名方式使用 signType中标示的签名方式,
	@XStreamAlias("Appsignature")
	private String paySign;

	public PayRequest() {
		this.timeStamp = System.currentTimeMillis() / 1000 + "";
		this.nonceStr = RandomUtil.generateString(16);
	}

	public PayRequest(String appId, String packageInfo, SignType signType,
			String paySign) {
		this.appId = appId;
		this.timeStamp = System.currentTimeMillis() / 1000 + "";
		this.nonceStr = RandomUtil.generateString(16);
		this.packageInfo = packageInfo;
		this.signType = signType.name();
		this.paySign = paySign;
	}

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

	@JSONField(name = "package")
	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(SignType signType) {
		this.signType = signType.name();
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
}
