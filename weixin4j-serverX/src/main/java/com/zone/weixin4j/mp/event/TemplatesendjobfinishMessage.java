package com.zone.weixin4j.mp.event;

import com.zone.weixin4j.message.event.EventMessage;
import com.zone.weixin4j.type.EventType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 模板消息事件推送(公众平台)
 * 
 * @className TemplatesendjobfinishMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月19日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">模板消息事件推送</a>
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
