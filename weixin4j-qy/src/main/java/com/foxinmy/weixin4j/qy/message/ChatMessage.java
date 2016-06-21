package com.foxinmy.weixin4j.qy.message;

import java.io.Serializable;

import com.foxinmy.weixin4j.qy.type.ChatType;
import com.foxinmy.weixin4j.tuple.ChatTuple;

/**
 * 会话消息对象
 *
 * @className ChatMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月1日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.tuple.Text
 * @see com.foxinmy.weixin4j.tuple.Image
 * @see com.foxinmy.weixin4j.tuple.File
 */
public class ChatMessage implements Serializable {

	private static final long serialVersionUID = -4973029130270955777L;

	/**
	 * 成员id|会话id
	 */
	private String targetId;
	/**
	 * 群聊|单聊
	 */
	private ChatType chatType;
	/**
	 * 发送人id
	 */
	private String senderId;
	/**
	 * 消息对象
	 */
	private ChatTuple chatTuple;

	public ChatMessage(String targetId, ChatType chatType, String senderId,
			ChatTuple chatTuple) {
		this.targetId = targetId;
		this.chatType = chatType;
		this.senderId = senderId;
		this.chatTuple = chatTuple;
	}

	public String getTargetId() {
		return targetId;
	}

	public ChatType getChatType() {
		return chatType;
	}

	public String getSenderId() {
		return senderId;
	}

	public ChatTuple getChatTuple() {
		return chatTuple;
	}

	@Override
	public String toString() {
		return "ChatMessage [targetId=" + targetId + ", chatType=" + chatType
				+ ", senderId=" + senderId + ", chatTuple=" + chatTuple + "]";
	}
}
