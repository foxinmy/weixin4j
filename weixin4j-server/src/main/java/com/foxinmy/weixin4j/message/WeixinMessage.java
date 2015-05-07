package com.foxinmy.weixin4j.message;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 被动消息
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WeixinMessage) {
			return ((WeixinMessage) obj).getMsgId() == msgId
					&& ((WeixinMessage) obj).getCreateTime() == createTime;
		}
		return false;
	}

	@Override
	public String toString() {
		return " toUserName=" + toUserName + ", fromUserName=" + fromUserName
				+ ", createTime=" + createTime + ", msgType=" + msgType
				+ ", msgId=" + msgId;
	}
}
