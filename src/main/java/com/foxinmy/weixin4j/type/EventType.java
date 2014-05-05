package com.foxinmy.weixin4j.type;

import com.foxinmy.weixin4j.msg.event.EventMessage;
import com.foxinmy.weixin4j.msg.event.LocationEventMessage;
import com.foxinmy.weixin4j.msg.event.MassEventMessage;
import com.foxinmy.weixin4j.msg.event.MenuEventMessage;
import com.foxinmy.weixin4j.msg.event.ScanEventMessage;
import com.foxinmy.weixin4j.msg.event.ScribeEventMessage;

/**
 * 
 * 事件类型
 * @author jy.hu
 *
 */
public enum EventType {
	subscribe(ScribeEventMessage.class), unsubscribe(ScribeEventMessage.class),
	scan(ScanEventMessage.class),click(MenuEventMessage.class),view(MenuEventMessage.class),
	location(LocationEventMessage.class),MASSSENDJOBFINISH(MassEventMessage.class);
	
	private Class<? extends EventMessage> eventClass;

	EventType(Class<? extends EventMessage> eventClass) {
		this.eventClass = eventClass;
	}

	public Class<? extends EventMessage> getEventClass() {
		return eventClass;
	}
}
