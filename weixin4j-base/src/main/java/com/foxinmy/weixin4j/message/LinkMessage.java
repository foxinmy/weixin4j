package com.foxinmy.weixin4j.message;

import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 链接消息
 * 
 * @className LinkMessage
 * @author jy.hu
 * @date 2014年4月6日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/10/79502792eef98d6e0c6e1739da387346.html#.E9.93.BE.E6.8E.A5.E6.B6.88.E6.81.AF">订阅号、服务号的链接消息</a>
 */
public class LinkMessage extends BaseMessage {

	private static final long serialVersionUID = 754952745115497030L;

	public LinkMessage() {
		super(MessageType.link.name());
	}

	/**
	 * 消息标题
	 */
	@XStreamAlias("Title")
	private String title;
	/**
	 * 消息描述
	 */
	@XStreamAlias("Description")
	private String description;
	/**
	 * 消息链接
	 */
	@XStreamAlias("Url")
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
