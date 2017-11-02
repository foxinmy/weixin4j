package com.zone.weixin4j.mp.event;

import com.zone.weixin4j.type.EventType;

/**
 * 关注/取消关注事件</br> <font color="red">包括直接关注与扫描关注</font>
 * 
 * @className ScribeEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454&token=&lang=zh_CN">订阅号、服务号的关注/取消关注事件</a>
 */
public class ScribeEventMessage extends ScanEventMessage {

	private static final long serialVersionUID = -6846321620262204915L;

	public ScribeEventMessage() {
		super(EventType.subscribe.name());
	}

	@Override
	public String toString() {
		return "ScribeEventMessage [" + super.toString() + "]";
	}
}
