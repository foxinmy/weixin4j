package com.foxinmy.weixin4j.mp.event;

import javax.xml.bind.annotation.XmlElement;

import com.foxinmy.weixin4j.message.event.EventMessage;
import com.foxinmy.weixin4j.type.EventType;

/**
 * 客服接入会话事件
 * 
 * @className KfCreateEventMessage
 * @author jy
 * @date 2015年3月22日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/2/6c20f3e323bdf5986cfcb33cbd3b829a.html#.E4.BC.9A.E8.AF.9D.E7.8A.B6.E6.80.81.E9.80.9A.E7.9F.A5.E4.BA.8B.E4.BB.B6">会话状态通知事件</a>
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
