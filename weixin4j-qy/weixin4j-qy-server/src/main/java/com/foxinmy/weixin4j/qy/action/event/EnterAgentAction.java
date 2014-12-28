package com.foxinmy.weixin4j.qy.action.event;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.event.EnterAgentEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 用户进入应用的事件推送
 * 
 * @className
 * @author jy
 * @date 2014年12月28日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.EnterAgentEventMessage
 * @see<a href=
 *        "http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E7.94.A8.E6.88.B7.E8.BF.9B.E5.85.A5.E5.BA.94.E7.94.A8.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81"
 *        >用户进入应用的事件推送</a>
 */
@ActionAnnotation(msgType = MessageType.event, eventType = { EventType.enter_agent })
public class EnterAgentAction extends DebugAction<EnterAgentEventMessage> {

}
