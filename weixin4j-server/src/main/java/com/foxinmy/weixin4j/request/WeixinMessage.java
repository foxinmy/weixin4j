package com.foxinmy.weixin4j.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 基本被动消息
 * 
 * @className WeixinMessage
 * @author jy
 * @date 2015年5月6日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement(name = "xml")
public class WeixinMessage implements Serializable {

	private static final long serialVersionUID = 7761192742840031607L;

	/**
	 * 开发者微信号
	 */
	private String toUserName;
	/**
	 * 发送方账号 即用户的openid
	 */
	private String fromUserName;
	/**
	 * 消息创建时间 系统毫秒数
	 */
	private long createTime;
	/**
	 * 消息类型
	 * 
	 */
	private String msgType;
	/**
	 * 消息ID 可用于排重
	 */
	private long msgId;
	/**
	 * 企业号独有的应用ID
	 */
	private String agentId;

	public WeixinMessage() {

	}

	public WeixinMessage(String msgType) {
		this.msgType = msgType;
	}

	public String getToUserName() {
		return toUserName;
	}

	@XmlElement(name = "ToUserName")
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	@XmlElement(name = "FromUserName")
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	@XmlElement(name = "CreateTime")
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	@XmlElement(name = "MsgType")
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public long getMsgId() {
		return msgId;
	}

	@XmlElement(name = "MsgId")
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getAgentId() {
		return agentId;
	}

	@XmlElement(name = "AgentID")
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		result = prime * result + (int) (createTime ^ (createTime >>> 32));
		result = prime * result
				+ ((fromUserName == null) ? 0 : fromUserName.hashCode());
		result = prime * result + (int) (msgId ^ (msgId >>> 32));
		result = prime * result + ((msgType == null) ? 0 : msgType.hashCode());
		result = prime * result
				+ ((toUserName == null) ? 0 : toUserName.hashCode());
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
		WeixinMessage other = (WeixinMessage) obj;
		if (msgId > 0l && other.getMsgId() > 0l) {
			return msgId == other.getMsgId();
		}
		return fromUserName.equals(other.getFromUserName())
				&& createTime == other.getCreateTime();
	}

	@Override
	public String toString() {
		String toString = " toUserName=" + toUserName + ", fromUserName="
				+ fromUserName + ", createTime=" + createTime + ", msgType="
				+ msgType;
		if (msgId > 0l) {
			toString += ", msgId=" + msgId;
		}
		if (agentId != null) {
			toString += ", agentId=" + agentId;
		}
		return toString;
	}
}
