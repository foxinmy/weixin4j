package com.foxinmy.weixin4j.msg;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 普通消息基类
 * <p>
 * <font color="red">回复图片等多媒体消息时需要预先上传多媒体文件到微信服务器,
 * 假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试</font>
 * </p>
 * 
 * @className BaseMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 */
public class BaseMessage extends BaseMsg {

	private static final long serialVersionUID = 7761192742840031607L;

	@XStreamAlias("MsgType")
	private MessageType msgType; // 消息类型
	@XStreamAlias("MsgId")
	private long msgId; // 消息ID

	public BaseMessage(MessageType msgType) {
		this.msgType = msgType;
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
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
		if (obj instanceof BaseMessage) {
			return ((BaseMessage) obj).getMsgId() == msgId;
		}
		return false;
	}
}
