package com.foxinmy.weixin4j.msg;

import com.foxinmy.weixin4j.model.BaseMsg;
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
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E6%94%B6%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF#.E9.93.BE.E6.8E.A5.E6.B6.88.E6.81.AF">链接消息</a>
 */
public class LinkMessage extends BaseMsg {

	private static final long serialVersionUID = 754952745115497030L;

	public LinkMessage() {
		super(MessageType.link.name());
	}

	@XStreamAlias("Title")
	private String title; // 消息标题
	@XStreamAlias("Description")
	private String description; // 消息描述
	@XStreamAlias("url")
	private String url; // 消息链接

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
		StringBuilder sb = new StringBuilder();
		sb.append("[LinkMessage ,toUserName=").append(super.getToUserName());
		sb.append(" ,fromUserName=").append(super.getFromUserName());
		sb.append(" ,msgType=").append(super.getMsgType());
		sb.append(" ,title=").append(title);
		sb.append(" ,description=").append(description);
		sb.append(" ,url=").append(url);
		sb.append(" ,createTime=").append(super.getCreateTime());
		sb.append(" ,msgId=").append(super.getMsgId()).append("]");
		return sb.toString();
	}
}
