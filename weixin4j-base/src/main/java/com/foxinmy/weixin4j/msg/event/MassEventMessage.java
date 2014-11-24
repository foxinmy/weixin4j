package com.foxinmy.weixin4j.msg.event;

import com.foxinmy.weixin4j.type.EventType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 群发消息事件推送
 * 
 * @className MassEventMessage
 * @author jy
 * @date 2014年4月27日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%AB%98%E7%BA%A7%E7%BE%A4%E5%8F%91%E6%8E%A5%E5%8F%A3#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81.E7.BE.A4.E5.8F.91.E7.BB.93.E6.9E.9C">群发回调</a>
 */
public class MassEventMessage extends EventMessage {

	private static final long serialVersionUID = -1660543255873723895L;

	public MassEventMessage() {
		super(EventType.massendjobfinish);
	}

	@XStreamAlias("Status")
	private String status;
	@XStreamAlias("TotalCount")
	private int totalCount;
	@XStreamAlias("FilterCount")
	private int filterCount;
	@XStreamAlias("SentCount")
	private int sentCount;
	@XStreamAlias("ErrorCount")
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
