package com.foxinmy.weixin4j.type;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.ImageMessage;
import com.foxinmy.weixin4j.msg.LinkMessage;
import com.foxinmy.weixin4j.msg.LocationMessage;
import com.foxinmy.weixin4j.msg.TextMessage;
import com.foxinmy.weixin4j.msg.VideoMessage;
import com.foxinmy.weixin4j.msg.VoiceMessage;
import com.foxinmy.weixin4j.msg.event.EventMessage;

/**
 * 
 * 消息类型
 * 
 * @author jy.hu
 * 
 */
public enum MessageType {
	text(TextMessage.class), image(ImageMessage.class), voice(
			VoiceMessage.class), video(VideoMessage.class), location(
			LocationMessage.class), link(LinkMessage.class), event(
			EventMessage.class), signature(null);
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
