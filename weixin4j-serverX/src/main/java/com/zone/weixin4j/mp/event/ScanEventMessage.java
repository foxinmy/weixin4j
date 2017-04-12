package com.zone.weixin4j.mp.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 扫描二维码事件
 * 
 * @className ScanEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454&token=&lang=zh_CN">扫描二维码事件</a>
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
