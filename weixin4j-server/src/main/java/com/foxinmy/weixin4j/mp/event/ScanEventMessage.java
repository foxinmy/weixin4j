package com.foxinmy.weixin4j.mp.event;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.foxinmy.weixin4j.message.event.EventMessage;
import com.foxinmy.weixin4j.type.EventType;

/**
 * 扫描二维码事件
 * 
 * @className ScanEventMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/2/5baf56ce4947d35003b86a9805634b1e.html#.E6.89.AB.E6.8F.8F.E5.B8.A6.E5.8F.82.E6.95.B0.E4.BA.8C.E7.BB.B4.E7.A0.81.E4.BA.8B.E4.BB.B6">扫描二维码事件</a>
 */
public class ScanEventMessage extends EventMessage {

	private static final long serialVersionUID = 8078674062833071562L;

	public ScanEventMessage() {
		super(EventType.scan.name());
	}

	public ScanEventMessage(String eventType) {
		super(eventType);
	}

	/**
	 * 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 */
	@XmlElement(name = "EventKey")
	private String eventKey;
	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	@XmlElement(name = "Ticket")
	private String ticket;

	public String getEventKey() {
		return eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	@XmlTransient
	public String getParameter() {
		return eventKey.replaceFirst("qrscene_", "");
	}

	@Override
	public String toString() {
		return "ScanEventMessage [eventKey=" + eventKey + ", ticket=" + ticket
				+ ", " + super.toString() + "]";
	}
}
