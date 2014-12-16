package com.foxinmy.weixin4j.msg.event;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 事件消息基类
 * 
 * @className EventMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/9/981d772286d10d153a3dc4286c1ee5b5.html">订阅号、服务号的事件推送</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6">企业号的事件消息</a>
 */
public class EventMessage extends BaseMsg {

	private static final long serialVersionUID = 7703667223814088865L;

	public EventMessage(EventType eventType) {
		super(MessageType.event.name());
		this.eventType = eventType;
	}

	@XStreamAlias("Event")
	private EventType eventType;

	public EventType getEventType() {
		return eventType;
	}

	@Override
	public String toString() {
		return "eventType=" + eventType + ", " + super.toString();
	}
}
