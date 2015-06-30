package com.foxinmy.weixin4j.mp.event;

import javax.xml.bind.annotation.XmlElement;

import com.foxinmy.weixin4j.message.event.EventMessage;
import com.foxinmy.weixin4j.type.EventType;

/**
 * 群发消息事件推送
 * 
 * @className MassEventMessage
 * @author jy
 * @date 2014年4月27日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81.E7.BE.A4.E5.8F.91.E7.BB.93.E6.9E.9C">群发回调</a>
 */
public class MassEventMessage extends EventMessage {

	private static final long serialVersionUID = -1660543255873723895L;

	public MassEventMessage() {
		super(EventType.masssendjobfinish.name());
	}

	/**
	 * 群发后的状态信息 为“send success”或“send fail”或“err(num)
	 */
	@XmlElement(name = "Status")
	private String status;
	/**
	 * group_id下粉丝数；或者openid_list中的粉丝数
	 */
	@XmlElement(name = "TotalCount")
	private int totalCount;
	/**
	 * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount =
	 * SentCount + ErrorCount
	 */
	@XmlElement(name = "FilterCount")
	private int filterCount;
	/**
	 * 发送成功的粉丝数
	 */
	@XmlElement(name = "SentCount")
	private int sentCount;
	/**
	 * 发送失败的粉丝数
	 */
	@XmlElement(name = "ErrorCount")
	private int errorCount;

	public String getStatus() {
		return status;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getFilterCount() {
		return filterCount;
	}

	public int getSentCount() {
		return sentCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	@Override
	public String toString() {
		return "MassEventMessage [status=" + status + ", totalCount="
				+ totalCount + ", filterCount=" + filterCount + ", sentCount="
				+ sentCount + ", errorCount=" + errorCount + ", "
				+ super.toString() + "]";
	}
}
