package com.foxinmy.weixin4j.msg.event;


/**
 * 关注/取消关注事件
 * <font color="red">包括直接关注与扫描关注</font>
 * @className ScribeEventMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E4%BA%8B%E4%BB%B6%E6%8E%A8%E9%80%81#.E5.85.B3.E6.B3.A8.2F.E5.8F.96.E6.B6.88.E5.85.B3.E6.B3.A8.E4.BA.8B.E4.BB.B6">关注/取消关注事件</a>
 * @see com.foxinmy.weixin4j.msg.BaseMessage
 * @see com.foxinmy.weixin4j.msg.BaseMessage#toXml()
 */
public class ScribeEventMessage extends ScanEventMessage {

	private static final long serialVersionUID = -6846321620262204915L;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ScribeEventMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType().name());
		sb.append(" ,eventType=").append(super.getEventType().name());
		sb.append(" ,eventKey=").append(super.getEventKey());
		sb.append(" ,ticket=").append(super.getTicket());
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
