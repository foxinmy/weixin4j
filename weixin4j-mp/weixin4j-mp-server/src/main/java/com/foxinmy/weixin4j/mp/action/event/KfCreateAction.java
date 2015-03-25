package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.KfCreateEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 客服接入会话消息
 * 
 * @className KfCreateAction
 * @author jy
 * @date 2015年3月25日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.KfCreateEventMessage
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.kf_create_session })
public class KfCreateAction extends DebugAction<KfCreateEventMessage> {

}
