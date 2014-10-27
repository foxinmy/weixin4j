package com.foxinmy.weixin4j.mp.action.event;

import com.foxinmy.weixin4j.mp.action.Action;
import com.foxinmy.weixin4j.mp.action.DebugAction;
import com.foxinmy.weixin4j.msg.event.LocationEventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 上报地理位置后触发
 * 
 * @className LocationAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.event.LocationEventMessage
 */
@Action(msgType = MessageType.event, eventType = { EventType.location })
public class LocationAction extends DebugAction<LocationEventMessage> {

}
