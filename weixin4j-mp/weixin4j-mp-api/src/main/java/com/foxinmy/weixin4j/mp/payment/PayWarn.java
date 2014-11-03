package com.foxinmy.weixin4j.mp.payment;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class PayWarn implements Serializable {

	private static final long serialVersionUID = 2334592957844332640L;

	@XStreamAlias("AppId")
	private String appid;

	@XStreamAlias("ErrorType")
	private String errortype;

	@XStreamAlias("Description")
	private String description;

	@XStreamAlias("AlarmContent")
	private String alarmcontent;

	@XStreamAlias("TimeStamp")
	private String timestamp;

	@XStreamAlias("AppSignature")
	private String appsignature;

	@XStreamAlias("SignMethod")
	private String signmethod;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getErrortype() {
		return errortype;
	}

	public void setErrortype(String errortype) {
		this.errortype = errortype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlarmcontent() {
		return alarmcontent;
	}

	public void setAlarmcontent(String alarmcontent) {
		this.alarmcontent = alarmcontent;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAppsignature() {
		return appsignature;
	}

	public void setAppsignature(String appsignature) {
		this.appsignature = appsignature;
	}

	public String getSignmethod() {
		return signmethod;
	}

	public void setSignmethod(String signmethod) {
		this.signmethod = signmethod;
	}

	@Override
	public String toString() {
		return "PayWarn [appid=" + appid + ", errortype=" + errortype
				+ ", description=" + description + ", alarmcontent="
				+ alarmcontent + ", timestamp=" + timestamp + ", appsignature="
				+ appsignature + ", signmethod=" + signmethod + "]";
	}
}
