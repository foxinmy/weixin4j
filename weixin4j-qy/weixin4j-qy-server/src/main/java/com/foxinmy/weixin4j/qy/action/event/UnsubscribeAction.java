package com.foxinmy.weixin4j.qy.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.ScribeEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 取消关注时触发
 * 
 * @className UnsubscribeAction
 * @author jy
 * @date 2014年10月10日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.ScribeEventMessage
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.unsubscribe })
public class UnsubscribeAction extends DebugAction<ScribeEventMessage> {

}
