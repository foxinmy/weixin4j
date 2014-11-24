package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * view类型菜单点击时触发
 * 
 * @className MenuViewAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.view })
public class MenuViewAction extends DebugAction<MenuEventMessage> {

}
