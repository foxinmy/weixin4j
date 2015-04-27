package com.foxinmy.weixin4j.type;


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
	text,
	/**
	 * 图片消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.ImageMessage
	 */
	image,
	/**
	 * 语音消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.VoiceMessage
	 */
	voice,
	/**
	 * 视频消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.VideoMessage
	 */
	video,
	/**
	 * 小视频消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.VideoMessage
	 */
	shortvideo,
	/**
	 * 位置消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.LocationMessage
	 */
	location,
	/**
	 * 链接消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.LinkMessage
	 */
	link,
	/**
	 * 事件消息
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.EventMessage
	 */
	event;
}
