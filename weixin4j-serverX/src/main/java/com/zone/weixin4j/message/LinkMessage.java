package com.zone.weixin4j.message;

import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.type.MessageType;

import javax.xml.bind.annotation.XmlElement;

/**
 * 链接消息
 * 
 * @className LinkMessage
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月6日
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140453&token=&lang=zh_CN">
 *      订阅号、服务号的链接消息</a>
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF#link.E6.B6.88.E6.81.AF">
 *      企业号的链接消息</a>
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
		return "LinkMessage [title=" + title + ", description=" + description + ", url=" + url + ", " + super.toString()
				+ "]";
	}
}
