package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.mp.action.Action;
import com.foxinmy.weixin4j.mp.action.DebugAction;
import com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 菜单点击click事件时触发
 * 
 * @className MenuClickAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage
 */
@Action(msgType = MessageType.event, eventType = { EventType.click })
public class MenuClickAction extends DebugAction<MenuEventMessage> {

}
