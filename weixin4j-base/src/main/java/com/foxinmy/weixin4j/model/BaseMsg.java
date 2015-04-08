package com.foxinmy.weixin4j.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 消息基类
 * 
 * @className BaseMsg
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 */
public class BaseMsg implements Serializable {

	private static final long serialVersionUID = 7761192742840031607L;

	/**
	 * 开发者微信号
	 */
	@XStreamAlias("ToUserName")
	private String toUserName;
	/**
	 * 发送方账号 即用户的openid
	 */
	@XStreamAlias("FromUserName")
	private String fromUserName;
	/**
	 * 消息创建时间 系统毫秒数
	 */
	@XStreamAlias("CreateTime")
	private long createTime = System.currentTimeMillis();
	/**
	 * 消息类型
	 * 
	 * @see com.foxinmy.weixin4j.type.MessageType
	 */
	@XStreamAlias("MsgType")
	private String msgType;
	/**
	 * 消息ID 可用于排重
	 */
	@XStreamAlias("MsgId")
	private long msgId;
	/**
	 * 企业号独有的应用ID
	 */
	@XStreamAlias("AgentID")
	private String agentId;

	public BaseMsg() {

	}

	public BaseMsg(String msgType) {
		this.msgType = msgType;
	}

	public BaseMsg(String toUserName, String fromUserName) {
		this(null, toUserName, fromUserName);
	}

	public BaseMsg(String msgType, String toUserName, String fromUserName) {
		this.msgType = msgType;
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public long getMsgId() {
		return msgId;
	}

	public String getAgentId() {
		return agentId;
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
		if (obj instanceof BaseMsg) {
			return ((BaseMsg) obj).getMsgId() == msgId;
		}
		return false;
	}

	@Override
	public String toString() {
		return "toUserName=" + toUserName + ", fromUserName=" + fromUserName
				+ ", createTime=" + createTime + ", msgType=" + msgType
				+ ", msgId=" + msgId + ", agentId=" + agentId;
	}
}
