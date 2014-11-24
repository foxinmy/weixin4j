package com.foxinmy.weixin4j.msg.event;

import com.foxinmy.weixin4j.type.EventType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 模板消息事件推送
 * 
 * @className TemplatesendjobfinishMessage
 * @author jy
 * @date 2014年9月19日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%A8%A1%E6%9D%BF%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3">模板消息事件推送</a>
 */
public class TemplatesendjobfinishMessage extends EventMessage {

	private static final long serialVersionUID = -2903359365988594012L;

	public TemplatesendjobfinishMessage() {
		super(EventType.templatesendjobfinish);
	}

	@XStreamAlias("Status")
	private String status; // 推送状态

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "TemplatesendjobfinishMessage [status=" + status + ", "
				+ super.toString() + "]";
	}
}
