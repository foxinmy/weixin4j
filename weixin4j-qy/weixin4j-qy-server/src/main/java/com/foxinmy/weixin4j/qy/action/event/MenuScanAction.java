package com.foxinmy.weixin4j.qy.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.menu.MenuScanEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 点击菜单扫描时触发
 * 
 * @className MenuScanAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.menu.MenuScanEventMessage
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.scancode_push,
		EventType.scancode_waitmsg })
public class MenuScanAction extends DebugAction<MenuScanEventMessage> {

}
