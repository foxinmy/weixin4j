package com.foxinmy.weixin4j.mp.msg.notify;

import com.foxinmy.weixin4j.mp.msg.model.Text;
import com.foxinmy.weixin4j.mp.type.ResponseType;

/**
 * 客服文本消息
 * 
 * @author jy.hu
 * @date 2014年4月4日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E6.96.87.E6.9C.AC.E6.B6.88.E6.81.AF">客服文本消息</a>
 * @see com.foxinmy.weixin.msg.model.Text
 * @see com.foxinmy.weixin4j.mp.msg.notify.BaseNotify
 * @see com.foxinmy.weixin4j.mp.msg.notify.BaseNotify#toJson()
 */
public class TextNotify extends BaseNotify {

	private static final long serialVersionUID = -7698823863398518425L;

	public TextNotify() {
		this(null, null);
	}

	public TextNotify(String content) {
		this(content,null);
	}

	public TextNotify(String content, String touser) {
		super(touser, ResponseType.text);
		this.text = new Text(content);
	}

	public void setContent(String content) {
		this.text = new Text(content);
	}

	private Text text;

	@Override
	public String toString() {
		return String.format("[TextNotify touser=%s ,msgtype=%s ,content=%s]",
				super.getTouser(), super.getMsgtype().name(),
				this.text.getContent());
	}
}
