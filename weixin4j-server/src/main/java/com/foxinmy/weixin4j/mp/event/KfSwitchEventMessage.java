package com.foxinmy.weixin4j.mp.event;

import javax.xml.bind.annotation.XmlElement;

import com.foxinmy.weixin4j.message.event.EventMessage;
import com.foxinmy.weixin4j.type.EventType;

/**
 * 客服转接会话事件
 * 
 * @className KfSwitchEventMessage
 * @author jy
 * @date 2015年3月22日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/2/6c20f3e323bdf5986cfcb33cbd3b829a.html#.E4.BC.9A.E8.AF.9D.E7.8A.B6.E6.80.81.E9.80.9A.E7.9F.A5.E4.BA.8B.E4.BB.B6">会话状态通知事件</a>
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
