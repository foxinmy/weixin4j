package com.zone.weixin4j.socket;

import com.zone.weixin4j.type.AccountType;
import com.zone.weixin4j.type.EncryptType;
import com.zone.weixin4j.util.AesToken;

import java.io.Serializable;
import java.util.Set;

/**
 * 消息传递
 * 
 * @className WeixinMessageTransfer
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月23日
 * @since JDK 1.6
 * @see
 */
public class WeixinMessageTransfer implements Serializable {

	private static final long serialVersionUID = 7779948135156353261L;

	/**
	 * aes & token
	 */
	private AesToken aesToken;
	/**
	 * 加密类型
	 */
	private EncryptType encryptType;
	/**
	 * 消息接收方
	 */
	private String toUserName;
	/**
	 * 消息发送方
	 */
	private String fromUserName;
	/**
	 * 账号
	 */
	private AccountType accountType;
	/**
	 * 消息类型
	 */
	private String msgType;
	/**
	 * 事件类型
	 */
	private String eventType;
	/**
	 * 节点集合
	 */
	private Set<String> nodeNames;

	public WeixinMessageTransfer(AesToken aesToken, EncryptType encryptType,
			String toUserName, String fromUserName, AccountType accountType,
			String msgType, String eventType, Set<String> nodeNames) {
		this.aesToken = aesToken;
		this.encryptType = encryptType;
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.accountType = accountType;
		this.msgType = msgType;
		this.eventType = eventType;
		this.nodeNames = nodeNames;
	}

	public AesToken getAesToken() {
		return aesToken;
	}

	public EncryptType getEncryptType() {
		return encryptType;
	}

	public String getToUserName() {
		return toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public String getMsgType() {
		return msgType;
	}

	public String getEventType() {
		return eventType;
	}

	public Set<String> getNodeNames() {
		return nodeNames;
	}

	@Override
	public String toString() {
		return "WeixinMessageTransfer [aesToken=" + aesToken + ", encryptType="
				+ encryptType + ", toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", accountType="
				+ accountType + ", msgType=" + msgType + ", eventType="
				+ eventType + ", nodeNames=" + nodeNames + "]";
	}
}
