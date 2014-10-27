package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.mp.action.Action;
import com.foxinmy.weixin4j.mp.action.DebugAction;
import com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 模板消息发送动作完成时触发
 * 
 * @className TemplateSendAction
 * @author jy
 * @date 2014年10月10日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
 */
@Action(msgType = MessageType.event, eventType = { EventType.templatesendjobfinish })
public class TemplateSendAction extends
		DebugAction<TemplatesendjobfinishMessage> {

}
