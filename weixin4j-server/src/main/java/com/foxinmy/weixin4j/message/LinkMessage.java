package com.foxinmy.weixin4j.message;

import javax.xml.bind.annotation.XmlElement;

import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 链接消息
 * 
 * @className LinkMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/10/79502792eef98d6e0c6e1739da387346.html#.E9.93.BE.E6.8E.A5.E6.B6.88.E6.81.AF">订阅号、服务号的链接消息</a>
 */
public class LinkMessage extends WeixinMessage {

	private static final long serialVersionUID = 754952745115497030L;

	public LinkMessage() {
		super(MessageType.link.name());
	}

	/**
	 * 消息标题
	 */
	@XmlElement(name = "Title")
	private String title;
	/**
	 * 消息描述
	 */
	@XmlElement(name = "Description")
	private String description;
	/**
	 * 消息链接
	 */
	@XmlElement(name = "Url")
	private String url;

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "LinkMessage [title=" + title + ", description=" + description
				+ ", url=" + url + ", " + super.toString() + "]";
	}
}
