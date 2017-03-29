package com.zone.weixin4j.dispatcher;

import com.zone.weixin4j.type.AccountType;
import com.zone.weixin4j.util.ServerToolkits;

import java.io.Serializable;

/**
 * 微信消息key
 * 
 * @className WeixinMessageKey
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月9日
 * @since JDK 1.6
 * @see
 */
public class WeixinMessageKey implements Serializable {

	private static final long serialVersionUID = -691330687850400289L;

	private String messageType;
	private String eventType;
	private AccountType accountType;

	public WeixinMessageKey(String messageType, String eventType,
			AccountType accountType) {
		this.messageType = messageType;
		this.eventType = eventType;
		this.accountType = accountType;
	}

	public String getMessageType() {
		return messageType;
	}

	public String getEventType() {
		return eventType;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result
				+ ((ServerToolkits.isBlank(eventType)) ? 0 : eventType.hashCode());
		result = prime
				* result
				+ ((ServerToolkits.isBlank(messageType)) ? 0 : messageType
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeixinMessageKey other = (WeixinMessageKey) obj;
		if (accountType != other.accountType)
			return false;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equalsIgnoreCase(other.eventType))
			return false;
		if (messageType == null) {
			if (other.messageType != null)
				return false;
		} else if (!messageType.equalsIgnoreCase(other.messageType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WeixinMessageKey [messageType=" + messageType + ", eventType="
				+ eventType + ", accountType=" + accountType + "]";
	}
}
