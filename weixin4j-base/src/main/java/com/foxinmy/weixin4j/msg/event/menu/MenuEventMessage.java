package com.foxinmy.weixin4j.msg.event.menu;

import com.foxinmy.weixin4j.msg.event.EventMessage;
import com.foxinmy.weixin4j.type.EventType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 自定义菜单事件(view|click)
 * 
 * @className MenuEventMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/9/981d772286d10d153a3dc4286c1ee5b5.html#.E7.82.B9.E5.87.BB.E8.8F.9C.E5.8D.95.E6.8B.89.E5.8F.96.E6.B6.88.E6.81.AF.E6.97.B6.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">订阅号、服务号的菜单事件</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E4.B8.8A.E6.8A.A5.E8.8F.9C.E5.8D.95.E4.BA.8B.E4.BB.B6">企业号的菜单事件</a>
 */
public class MenuEventMessage extends EventMessage {

	private static final long serialVersionUID = -1049672447995366063L;

	public MenuEventMessage() {
		super(EventType.click);
	}

	public MenuEventMessage(EventType eventType) {
		super(eventType);
	}

	@XStreamAlias("EventKey")
	private String eventKey; // 事件KEY值，与自定义菜单接口中KEY值对应

	public String getEventKey() {
		return eventKey;
	}

	@Override
	public String toString() {
		return "MenuEventMessage [eventKey=" + eventKey + ", "
				+ super.toString() + "]";
	}
}
