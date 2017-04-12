package com.zone.weixin4j.mp.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 客服关闭会话事件
 * 
 * @className KfCloseEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月22日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455864026&token=&lang=zh_CN">会话状态通知事件</a>
 */
public class KfCloseEventMessage extends EventMessage {

	private static final long serialVersionUID = 3644449346935205541L;
	
	public KfCloseEventMessage() {
		super(EventType.kf_close_session.name());
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
		return "KfCloseEventMessage [kfAccount=" + kfAccount + ", ="
				+ super.toString() + "]";
	}
}
