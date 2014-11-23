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
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E4%BA%8B%E4%BB%B6%E6%8E%A8%E9%80%81#.E7.82.B9.E5.87.BB.E8.8F.9C.E5.8D.95.E6.8B.89.E5.8F.96.E6.B6.88.E6.81.AF.E6.97.B6.E7.9A.84.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">菜单事件</a>
 */
public class MenuEventMessage extends EventMessage {

	private static final long serialVersionUID = -1049672447995366063L;

	public MenuEventMessage() {
		super(EventType.click);
	}

	@XStreamAlias("EventKey")
	private String eventKey; // 事件KEY值，与自定义菜单接口中KEY值对应

	public String getEventKey() {
		return eventKey;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[MenuEventMessage ,toUserName=").append(
				super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType());
		sb.append(" ,eventType=").append(super.getEventType().name());
		sb.append(" ,eventKey=").append(eventKey);
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
