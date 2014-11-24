package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.ScanEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 扫描事件时触发
 * 
 * @className ScanAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.ScanEventMessage
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.scan })
public class ScanAction extends DebugAction<ScanEventMessage> {

}
