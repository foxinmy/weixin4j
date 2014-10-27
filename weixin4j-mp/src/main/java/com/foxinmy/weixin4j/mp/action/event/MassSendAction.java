package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.mp.action.Action;
import com.foxinmy.weixin4j.mp.action.DebugAction;
import com.foxinmy.weixin4j.msg.event.MassEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 群发消息发送动作完成后触发
 * 
 * @className MassSendAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.MassEventMessage
 */
@Action(msgType = MessageType.event, eventType = { EventType.massendjobfinish })
public class MassSendAction extends DebugAction<MassEventMessage> {

}
