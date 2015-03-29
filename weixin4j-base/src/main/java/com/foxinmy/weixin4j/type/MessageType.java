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
	/**
	 * 文字消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.TextMessage
	 */
	text(TextMessage.class),
	/**
	 * 图片消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.ImageMessage
	 */
	image(ImageMessage.class),
	/**
	 * 语音消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.VoiceMessage
	 */
	voice(VoiceMessage.class),
	/**
	 * 视频消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.VideoMessage
	 */
	video(VideoMessage.class),
	/**
	 * 位置消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.LocationMessage
	 */
	location(LocationMessage.class),
	/**
	 * 链接消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.LinkMessage
	 */
	link(LinkMessage.class),
	/**
	 * 事件消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.EventMessage
	 */
	event(EventMessage.class);
	private Class<? extends BaseMsg> messageClass;

	MessageType(Class<? extends BaseMsg> messageClass) {
		this.messageClass = messageClass;
	}

	public Class<? extends BaseMsg> getMessageClass() {
		return messageClass;
	}
}
