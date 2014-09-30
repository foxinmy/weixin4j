package com.foxinmy.weixin4j.msg.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 扫描二维码事件
 * 
 * @className ScanEventMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6%E6%8E%A8%E9%80%81#.E6.89.AB.E6.8F.8F.E5.B8.A6.E5.8F.82.E6.95.B0.E4.BA.8C.E7.BB.B4.E7.A0.81.E4.BA.8B.E4.BB.B6">扫描二维码事件</a>
 */
public class ScanEventMessage extends EventMessage {

	public ScanEventMessage() {
		super(null);
	}

	private static final long serialVersionUID = 8078674062833071562L;
	private static final String PARA_PREFIX = "qrscene_";

	@XStreamAlias("EventKey")
	private String eventKey; // 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	@XStreamAlias("Ticket")
	private String ticket; // 二维码的ticket，可用来换取二维码图片

	public String getEventKey() {
		return eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public String getParameter() {
		return eventKey.replace(PARA_PREFIX, "");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ScanEventMessage ,toUserName=").append(
				super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,eventType=").append(super.getEventType().name());
		sb.append(" ,eventKey=").append(eventKey);
		sb.append(" ,ticket=").append(ticket);
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
