package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.mp.action.Action;
import com.foxinmy.weixin4j.mp.action.DebugAction;
import com.foxinmy.weixin4j.msg.event.ScribeEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 关注时触发
 * 
 * @className SubscribeAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.ScanEventMessage
 */
@Action(msgType = MessageType.event, eventType = { EventType.subscribe })
public class SubscribeAction extends DebugAction<ScribeEventMessage> {

}
