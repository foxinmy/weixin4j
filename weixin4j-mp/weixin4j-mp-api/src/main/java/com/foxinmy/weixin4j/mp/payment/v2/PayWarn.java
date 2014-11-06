package com.foxinmy.weixin4j.mp.payment.v2;

import com.foxinmy.weixin4j.mp.payment.PayBaseInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class PayWarn extends PayBaseInfo {

	private static final long serialVersionUID = 2334592957844332640L;

	@XStreamAlias("ErrorType")
	private String errortype;

	@XStreamAlias("Description")
	private String description;

	@XStreamAlias("AlarmContent")
	private String alarmcontent;

	public String getErrortype() {
		return errortype;
	}

	public String getDescription() {
		return description;
	}

	public String getAlarmcontent() {
		return alarmcontent;
	}

	@Override
	public String toString() {
		return "PayWarn [errortype=" + errortype + ", description="
				+ description + ", alarmcontent=" + alarmcontent
				+ ", getAppId()=" + getAppId() + ", getTimeStamp()="
				+ getTimeStamp() + ", getNonceStr()=" + getNonceStr()
				+ ", getPaySign()=" + getPaySign() + ", getSignType()="
				+ getSignType() + "]";
	}
}