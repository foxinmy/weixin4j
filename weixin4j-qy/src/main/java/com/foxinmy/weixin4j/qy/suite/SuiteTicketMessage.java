package com.foxinmy.weixin4j.qy.suite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuiteTicketMessage implements Serializable {

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
	private SuiteEventType eventType;
	/**
	 * 时间戳
	 */
	@XmlElement(name = "TimeStamp")
	private long timeStamp;
	/**
	 * Ticket内容
	 */
	@XmlElement(name = "SuiteTicket")
	private String SuiteTicket;
	/**
	 * 授权方企业号的corpid
	 */
	@XmlElement(name = "AuthCorpId")
	private String authCorpId;

	public String getSuiteId() {
		return suiteId;
	}

	public SuiteEventType getEventType() {
		return eventType;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public String getSuiteTicket() {
		return SuiteTicket;
	}

	public String getAuthCorpId() {
		return authCorpId;
	}

	@Override
	public String toString() {
		return "SuiteTicketMessage [suiteId=" + suiteId + ", eventType="
				+ eventType + ", timeStamp=" + timeStamp + ", SuiteTicket="
				+ SuiteTicket + ", authCorpId=" + authCorpId + "]";
	}
}
