package com.foxinmy.weixin4j.mp.oldpayment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.payment.PayBaseInfo;

/**
 * V2告警通知
 * 
 * @className PayWarn
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年12月31日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayWarn extends PayBaseInfo {

	private static final long serialVersionUID = 2334592957844332640L;

	/**
	 * 错误代号 1001=发货超时
	 */
	@JSONField(name = "ErrorType")
	@XmlElement(name = "ErrorType")
	private String errortype;
	/**
	 * 错误描述
	 */
	@JSONField(name = "Description")
	@XmlElement(name = "Description")
	private String description;
	/**
	 * 错误详情
	 */
	@JSONField(name = "AlarmContent")
	@XmlElement(name = "AlarmContent")
	private String alarmcontent;

	public PayWarn() {

	}

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
				+ description + ", alarmcontent=" + alarmcontent + ", "
				+ super.toString() + "]";
	}
}