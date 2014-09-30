package com.foxinmy.weixin4j.type;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.TextMessage;
import com.foxinmy.weixin4j.msg.event.EventMessage;
import com.foxinmy.weixin4j.msg.in.ImageMessage;
import com.foxinmy.weixin4j.msg.in.LinkMessage;
import com.foxinmy.weixin4j.msg.in.LocationMessage;
import com.foxinmy.weixin4j.msg.in.VideoMessage;
import com.foxinmy.weixin4j.msg.in.VoiceMessage;
import com.foxinmy.weixin4j.msg.out.ArticleMessage;
import com.foxinmy.weixin4j.msg.out.MusicMessage;
import com.foxinmy.weixin4j.msg.out.TransferMessage;

/**
 * 
 * 消息类型
 * 
 * @author jy.hu
 * 
 */
public enum MessageType {
	// 接收到的消息类型
	text(TextMessage.class), image(ImageMessage.class), voice(
			VoiceMessage.class), video(VideoMessage.class), location(
			LocationMessage.class), link(LinkMessage.class), event(
			EventMessage.class),
	// 发送的消息类型
	music(MusicMessage.class), news(ArticleMessage.class), transfer_customer_service(
			TransferMessage.class);
	private Class<? extends BaseMessage> messageClass;

	MessageType(Class<? extends BaseMessage> messageClass) {
		this.messageClass = messageClass;
	}

	public void setMessageClass(Class<? extends BaseMessage> messageClass) {
		this.messageClass = messageClass;
	}

	public Class<? extends BaseMessage> getMessageClass() {
		return messageClass;
	}
}
