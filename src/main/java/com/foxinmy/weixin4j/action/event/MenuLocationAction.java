package com.foxinmy.weixin4j.action.event;

import com.foxinmy.weixin4j.action.Action;
import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.msg.event.menu.MenuLocationEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 菜单发送地理位置时触发
 * 
 * @className MenuLocationAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.menu.MenuLocationEventMessage
 */
@Action(msgType = MessageType.event, eventType = { EventType.location_select })
public class MenuLocationAction extends DebugAction<MenuLocationEventMessage> {

}
