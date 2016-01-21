package com.foxinmy.weixin4j.mp.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 认证失败事件(资质认证失败/名称认证失败)
 * 
 * @className VerifyFailEventMessage
 * @author jy
 * @date 2015年10月25日
 * @since JDK 1.6
 * @see
 */
public class VerifyFailEventMessage extends VerifyExpireEventMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2155899086751787490L;

	/**
	 * 失败发生时间 (整形)，时间戳
	 */
	@XmlElement(name = "FailTime")
	private long failTime;
	/**
	 * 认证失败的原因
	 */
	@XmlElement(name = "FailReason")
	private String failReason;

	public long getFailTime() {
		return failTime;
	}
	
	@XmlTransient
	public Date getFormatFailTime() {
		return new Date(failTime * 1000l);
	}

	public String getFailReason() {
		return failReason;
	}

	@Override
	public String toString() {
		return "VerifyFailEventMessage [failTime=" + failTime + ", failReason="
				+ failReason + ", " + super.toString() + "]";
	}
}
