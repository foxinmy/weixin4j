package com.foxinmy.weixin4j.msg.event;

import com.foxinmy.weixin4j.type.EventType;

/**
 * 关注/取消关注事件</br> <font color="red">包括直接关注与扫描关注</font>
 * 
 * @className ScribeEventMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/2/5baf56ce4947d35003b86a9805634b1e.html#.E5.85.B3.E6.B3.A8.2F.E5.8F.96.E6.B6.88.E5.85.B3.E6.B3.A8.E4.BA.8B.E4.BB.B6">订阅号、服务号的关注/取消关注事件</a>
 * @see <a href="http://qydev.weixin.qq.com/wiki/index.php?title=%
 *      E5%85%B3%E6%B3%A8%E4%B8%
 *      8E%E5%8F%96%E6%B6%88%E5%85%B3%E6%B3%A8#.E5.85.B3.E6.B3.A8.2F.E5.8F.96.E6.B6.88.E5.85.B3.E6.B3.A8.E4.BA.8B.E4.BB.B6.E7.9A.84.E6.8E.A8.E9.80.81">企业号的关注/取消关注
 *      事 件 </a>
 */
public class ScribeEventMessage extends ScanEventMessage {

	private static final long serialVersionUID = -6846321620262204915L;

	public ScribeEventMessage() {
		super(EventType.subscribe);
	}

	@Override
	public String toString() {
		return "ScribeEventMessage [" + super.toString() + "]";
	}
}
