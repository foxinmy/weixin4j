package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.KfSwitchEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 客服转接会话消息
 * 
 * @className KfSwitchAction
 * @author jy
 * @date 2015年3月25日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.KfSwitchEventMessage
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.kf_switch_session })
public class KfSwitchAction extends DebugAction<KfSwitchEventMessage> {

}
