package com.foxinmy.weixin4j.type;

import com.foxinmy.weixin4j.model.BaseMsg;
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
			EventMessage.class);
	private Class<? extends BaseMsg> messageClass;

	MessageType(Class<? extends BaseMsg> messageClass) {
		this.messageClass = messageClass;
	}

	public Class<? extends BaseMsg> getMessageClass() {
		return messageClass;
	}
}
