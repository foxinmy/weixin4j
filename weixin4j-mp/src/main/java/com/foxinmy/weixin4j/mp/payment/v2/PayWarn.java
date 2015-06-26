package com.foxinmy.weixin4j.mp.payment.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.foxinmy.weixin4j.payment.PayBaseInfo;

/**
 * V2告警通知
 * 
 * @className PayWarn
 * @author jy
 * @date 2014年12月31日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayWarn extends PayBaseInfo {

	private static final long serialVersionUID = 2334592957844332640L;

	/**
	 * 错误代号 1001=发货超时
	 */
	@XmlElement(name = "ErrorType")
	private String errortype;
	/**
	 * 错误描述
	 */
	@XmlElement(name = "Description")
	private String description;
	/**
	 * 错误详情
	 */
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