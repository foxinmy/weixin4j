package com.zone.weixin4j.mp.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 客服转接会话事件
 * 
 * @className KfSwitchEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月22日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455864026&token=&lang=zh_CN">会话状态通知事件</a>
 */
public class KfSwitchEventMessage extends EventMessage {

	private static final long serialVersionUID = 4319501074109623413L;

	public KfSwitchEventMessage() {
		super(EventType.kf_switch_session.name());
	}

	/**
	 * 来自的客服账号
	 */
	@XmlElement(name = "FromKfAccount")
	private String fromKfAccount;
	/**
	 * 转移给客服账号
	 */
	@XmlElement(name = "ToKfAccount")
	private String toKfAccount;

	public String getFromKfAccount() {
		return fromKfAccount;
	}

	public String getToKfAccount() {
		return toKfAccount;
	}

	@Override
	public String toString() {
		return "KfSwitchEventMessage [fromKfAccount=" + fromKfAccount
				+ ", toKfAccount=" + toKfAccount + "]";
	}
}
