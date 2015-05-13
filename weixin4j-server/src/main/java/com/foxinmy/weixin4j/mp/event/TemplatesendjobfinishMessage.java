package com.foxinmy.weixin4j.mp.event;

import javax.xml.bind.annotation.XmlElement;

import com.foxinmy.weixin4j.message.event.EventMessage;
import com.foxinmy.weixin4j.type.EventType;

/**
 * 模板消息事件推送(公众平台)
 * 
 * @className TemplatesendjobfinishMessage
 * @author jy
 * @date 2014年9月19日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81">模板消息事件推送</a>
 */
public class TemplatesendjobfinishMessage extends EventMessage {

	private static final long serialVersionUID = -2903359365988594012L;

	public TemplatesendjobfinishMessage() {
		super(EventType.templatesendjobfinish.name());
	}

	/**
	 * 推送状态 如failed: system failed
	 */
	@XmlElement(name = "Status")
	private String status;

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "TemplatesendjobfinishMessage [status=" + status + ", "
				+ super.toString() + "]";
	}
}
