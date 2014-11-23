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

	@XStreamAlias("ToUserName")
	private String toUserName; // 开发者微信号
	@XStreamAlias("FromUserName")
	private String fromUserName; // 发送方帐号（一个OpenID）
	@XStreamAlias("CreateTime")
	private long createTime = System.currentTimeMillis(); // 消息创建时间 （整型）
	@XStreamAlias("MsgType")
	private String msgType; // 消息类型
	@XStreamAlias("MsgId")
	private long msgId; // 消息ID

	public BaseMsg() {

	}

	public BaseMsg(String msgType) {
		this(msgType, null, null);
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

	public void setMsgId(long msgId) {
		this.msgId = msgId;
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
		return "BaseMsg [toUserName=" + toUserName + ", fromUserName="
				+ fromUserName + ", createTime=" + createTime + ", msgType="
				+ msgType + ", msgId=" + msgId + "]";
	}
}
