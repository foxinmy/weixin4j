package com.zone.weixin4j.mp.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 群发消息事件推送
 * 
 * @className MassEventMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月27日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">群发回调</a>
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
