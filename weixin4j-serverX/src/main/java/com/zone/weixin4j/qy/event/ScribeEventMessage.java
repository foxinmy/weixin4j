package com.zone.weixin4j.qy.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

/**
 * 关注/取消关注事件</br> <font color="red">包括直接关注与扫描关注</font>
 * 
 * @className ScribeEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E6.88.90.E5.91.98.E5.85.B3.E6.B3.A8.2F.E5.8F.96.E6.B6.88.E5.85.B3.E6.B3.A8.E4.BA.8B.E4.BB.B6">成员关注/取消关注事件</a>
 */
public class ScribeEventMessage extends EventMessage {

	private static final long serialVersionUID = -6846321620262204915L;

	public ScribeEventMessage() {
		super(EventType.subscribe.name());
	}

	@Override
	public String toString() {
		return "ScribeEventMessage [" + super.toString() + "]";
	}
}
