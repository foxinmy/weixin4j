package com.zone.weixin4j.mp.component;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 组件消息
 * 
 * @className ComponentMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月5日
 * @since JDK 1.6
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComponentMessage implements Serializable {

	private static final long serialVersionUID = -7243616276403632118L;
	/**
	 * 第三方平台appid
	 */
	@XmlElement(name = "AppId")
	private String appId;
	/**
	 * 事件类型
	 */
	@XmlElement(name = "InfoType")
	private String eventType;
	/**
	 * 时间戳
	 */
	@XmlElement(name = "CreateTime")
	private long createTime;
	/**
	 * Ticket内容
	 */
	@XmlElement(name = "ComponentVerifyTicket")
	private String verifyTicket;
	/**
	 * 授权方的Appid
	 */
	@XmlElement(name = "AuthorizerAppid")
	private String authAppId;
	/**
	 * 授权码，可用于换取公众号的接口调用凭据
	 */
	@XmlElement(name = "AuthorizationCode")
	private String authCode;
	/**
	 * 授权码过期时间
	 */
	@XmlElement(name = "AuthorizationCodeExpiredTime")
	private long authCodeExpiredTime;

	public String getAppId() {
		return appId;
	}

	public String getEventType() {
		return eventType;
	}

	@XmlTransient
	public ComponentEventType getFormatEventType() {
		return ComponentEventType.valueOf(eventType);
	}

	public long getCreateTime() {
		return createTime;
	}

	@XmlTransient
	public Date getFormatCreateTime() {
		return createTime > 0l ? new Date(createTime * 1000l) : null;
	}

	public String getVerifyTicket() {
		return verifyTicket;
	}

	public String getAuthAppId() {
		return authAppId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public long getAuthCodeExpiredTime() {
		return authCodeExpiredTime;
	}

	@XmlTransient
	public Date getFormatAuthCodeExpiredTime() {
		return authCodeExpiredTime > 0l ? new Date(authCodeExpiredTime * 1000l) : null;
	}

	@Override
	public String toString() {
		return "ComponentMessage [appId=" + appId + ", eventType=" + eventType + ", createTime=" + createTime
				+ ", verifyTicket=" + verifyTicket + ", authAppId=" + authAppId + ", authCode=" + authCode
				+ ", authCodeExpiredTime=" + authCodeExpiredTime + "]";
	}
}
