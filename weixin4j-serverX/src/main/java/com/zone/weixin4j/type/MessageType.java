package com.zone.weixin4j.type;


/**
 * 
 * 消息类型
 * 
 * @author jinyu(foxinmy@gmail.com)
 * 
 */
public enum MessageType {
	/**
	 * 文字消息
	 * 
	 * @see com.foxinmy.weixin4j.message.TextMessage
	 */
	text,
	/**
	 * 图片消息
	 * 
	 * @see com.foxinmy.weixin4j.message.ImageMessage
	 */
	image,
	/**
	 * 语音消息
	 * 
	 * @see com.foxinmy.weixin4j.message.VoiceMessage
	 */
	voice,
	/**
	 * 视频消息
	 * 
	 * @see com.foxinmy.weixin4j.message.VideoMessage
	 */
	video,
	/**
	 * 小视频消息
	 * 
	 * @see com.foxinmy.weixin4j.message.VideoMessage
	 */
	shortvideo,
	/**
	 * 位置消息
	 * 
	 * @see com.foxinmy.weixin4j.message.LocationMessage
	 */
	location,
	/**
	 * 链接消息
	 * 
	 * @see com.foxinmy.weixin4j.message.LinkMessage
	 */
	link,
	/**
	 * 事件消息
	 * 
	 * @see com.foxinmy.weixin4j.message.event.EventMessage
	 */
	event;
}
