package com.foxinmy.weixin4j.msg.event;

import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.type.EventType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

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
		super(EventType.masssendjobfinish);
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

	@XStreamOmitField
	private final static Map<String, String> statusMap;
	static {
		statusMap = new HashMap<String, String>();
		statusMap.put("sendsuccess", "发送成功");
		statusMap.put("send_success", "发送成功");
		statusMap.put("success", "发送成功");
		statusMap.put("send success", "发送成功");
		statusMap.put("sendfail", "发送失败");
		statusMap.put("send_fail", "发送失败");
		statusMap.put("fail", "发送失败");
		statusMap.put("send fail", "发送失败");
		statusMap.put("err(10001)", "涉嫌广告");
		statusMap.put("err(20001)", "涉嫌政治");
		statusMap.put("err(20004)", "涉嫌社会");
		statusMap.put("err(20006)", "涉嫌违法犯罪");
		statusMap.put("err(20008)", "涉嫌欺诈");
		statusMap.put("err(20013)", "涉嫌版权");
		statusMap.put("err(22000)", "涉嫌互推(互相宣传)");
		statusMap.put("err(21000)", "涉嫌其他");
	}

	public String getStatus() {
		return status;
	}

	/**
	 * 发送状态描述<br/>
	 * err(10001,涉嫌广告) err(20001,涉嫌政治) err(20004,涉嫌社会)<br/>
	 * err(20002,涉嫌色情) err(20006,涉嫌违法犯罪) err(20008,涉嫌欺诈)<br/>
	 * err(20013,涉嫌版权) err(22000,涉嫌互推(互相宣传) err(21000,涉嫌其他)
	 * 
	 * @param status
	 * @return 中文描述
	 */
	public String getStatusDesc() {
		return statusMap.get(status.toLowerCase());
	}

	/**
	 * 发送状态描述<br/>
	 * err(10001,涉嫌广告) err(20001,涉嫌政治) err(20004,涉嫌社会)<br/>
	 * err(20002,涉嫌色情) err(20006,涉嫌违法犯罪) err(20008,涉嫌欺诈)<br/>
	 * err(20013,涉嫌版权) err(22000,涉嫌互推(互相宣传) err(21000,涉嫌其他)
	 * 
	 * @param status
	 * @return 中文描述
	 */
	public static String getStatusDesc(String status) {
		return statusMap.get(status.toLowerCase());
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
