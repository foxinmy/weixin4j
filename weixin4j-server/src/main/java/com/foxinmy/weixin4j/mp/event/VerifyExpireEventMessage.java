package com.foxinmy.weixin4j.mp.event;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.foxinmy.weixin4j.message.event.EventMessage;
import com.foxinmy.weixin4j.type.EventType;

/**
 * 认证通知(资质认证成功/名称认证成功/年审通知/认证过期失效通知)
 * 
 * @className VerifyExpireEventMessage
 * @author jy
 * @date 2015年10月25日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/1/7f81dec16b801b34629091094c099439.html">认证事件</a>
 */
public class VerifyExpireEventMessage extends EventMessage {

	private static final long serialVersionUID = -4309074299189681095L;

	public VerifyExpireEventMessage() {
		super(EventType.annual_renew.name());
	}

	/**
	 * 有效期 (整形)，指的是时间戳，将于该时间戳认证过期
	 */
	@XmlElement(name = "EventKey")
	private long expiredTime;

	public long getExpiredTime() {
		return expiredTime;
	}

	@XmlTransient
	public Date getFormatExpiredTime() {
		return new Date(expiredTime * 1000l);
	}

	@Override
	public String toString() {
		return "VerifyExpireEventMessage [expiredTime=" + expiredTime + ", "
				+ super.toString() + "]";
	}
}
