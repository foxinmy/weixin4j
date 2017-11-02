package com.zone.weixin4j.qy.suite;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 套件消息
 * 
 * @className SuiteMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月23日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuiteMessage implements Serializable {

	private static final long serialVersionUID = 6457919241019021514L;
	/**
	 * 应用套件的SuiteId
	 */
	@XmlElement(name = "SuiteId")
	private String suiteId;
	/**
	 * 事件类型
	 */
	@XmlElement(name = "InfoType")
	private String eventType;
	/**
	 * 时间戳
	 */
	@XmlElement(name = "TimeStamp")
	private long timeStamp;
	/**
	 * Ticket内容
	 */
	@XmlElement(name = "SuiteTicket")
	private String suiteTicket;
	/**
	 * 授权方企业号的corpid
	 */
	@XmlElement(name = "AuthCorpId")
	private String authCorpId;

	public String getSuiteId() {
		return suiteId;
	}

	public String getEventType() {
		return eventType;
	}

	@XmlTransient
	public SuiteEventType getFormatEventType() {
		return SuiteEventType.valueOf(eventType);
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	@XmlTransient
	public Date getFormatTimeStamp() {
		return timeStamp > 0l ? new Date(timeStamp * 1000l) : null;
	}

	public String getSuiteTicket() {
		return suiteTicket;
	}

	public String getAuthCorpId() {
		return authCorpId;
	}

	@Override
	public String toString() {
		return "SuiteMessage [suiteId=" + suiteId + ", eventType="
				+ eventType + ", timeStamp=" + timeStamp + ", suiteTicket="
				+ suiteTicket + ", authCorpId=" + authCorpId + "]";
	}
}
