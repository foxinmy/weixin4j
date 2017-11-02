package com.zone.weixin4j.qy.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 用户进入应用的事件推送(企业号)本事件只有在应用的回调模式中打开上报开关时上报
 * 
 * @className EnterAgentEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年12月28日
 * @since JDK 1.6
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6#.E7.94.A8.E6.88.B7.E8.BF.9B.E5.85.A5.E5.BA.94.E7.94.A8.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81"
 *      >用户进入应用的事件推送</a>
 */
public class EnterAgentEventMessage extends EventMessage {

	private static final long serialVersionUID = 7675732524832500820L;

	public EnterAgentEventMessage() {
		super(EventType.enter_agent.name());
	}

	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应
	 */
	@XmlElement(name = "EventKey")
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	@Override
	public String toString() {
		return "EnterAgentEventMessage [eventKey=" + eventKey + ", "
				+ super.toString() + "]";
	}
}
