package com.zone.weixin4j.mp.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 客服接入会话事件
 * 
 * @className KfCreateEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月22日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455864026&token=&lang=zh_CN">会话状态通知事件</a>
 */
public class KfCreateEventMessage extends EventMessage {

	private static final long serialVersionUID = -8968189700999202108L;

	public KfCreateEventMessage() {
		super(EventType.kf_create_session.name());
	}

	/**
	 * 客服账号
	 */
	@XmlElement(name = "KfAccount")
	private String kfAccount;

	public String getKfAccount() {
		return kfAccount;
	}

	@Override
	public String toString() {
		return "KfCreateEventMessage [kfAccount=" + kfAccount + ", ="
				+ super.toString() + "]";
	}
}
