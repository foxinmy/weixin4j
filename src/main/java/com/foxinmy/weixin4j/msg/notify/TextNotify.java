package com.foxinmy.weixin4j.msg.notify;

import com.foxinmy.weixin4j.type.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 客服文本消息
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E6.96.87.E6.9C.AC.E6.B6.88.E6.81.AF">客服文本消息</a>
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify
 * @see com.foxinmy.weixin4j.msg.notify.BaseNotify#toJson()
 */
public class TextNotify extends BaseNotify {

	private static final long serialVersionUID = -7698823863398518425L;

	public TextNotify() {
		super(MessageType.text);
	}

	public TextNotify(String content) {
		super(MessageType.text);
		this.text = new $(content);
	}

	public TextNotify(String content, String touser) {
		super(touser, MessageType.text);
		this.text = new $(content);
	}

	@XStreamAlias("text")
	private $ text;

	private static class $ {
		@XStreamAlias("content")
		private String content;

		public $(String content) {
			this.content = content;
		}
	}
	@Override
	public String toString() {
		return String.format("[TextNotify touser=%s ,msgtype=%s ,content=%s]", super.getTouser(), super.getMsgtype().name(), this.text.content);
	}
}
