package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.KfCloseEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 客服关闭会话消息
 * 
 * @className KfCloseAction
 * @author jy
 * @date 2015年3月25日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.KfCloseEventMessage
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.kf_close_session })
public class KfCloseAction extends DebugAction<KfCloseEventMessage> {

}
