package com.foxinmy.weixin4j.action.event;

import com.foxinmy.weixin4j.action.Action;
import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 菜单发送图片时触发
 * 
 * @className MenuPhotoAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage
 */
@Action(msgType = MessageType.event, eventType = {
		EventType.pic_photo_or_album, EventType.pic_sysphoto,
		EventType.pic_weixin })
public class MenuPhotoAction extends DebugAction<MenuPhotoEventMessage> {

}
