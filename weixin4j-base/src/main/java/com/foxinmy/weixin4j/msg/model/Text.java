package com.foxinmy.weixin4j.msg.model;

import com.foxinmy.weixin4j.type.MediaType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本对象
 * <p>
 * <font color="red">可用于「被动消息」「客服消息」「群发消息」</font>
 * </p>
 * 
 * @className Text
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("Content")
public class Text extends Base implements Responseable, Notifyable, Massable {

	private static final long serialVersionUID = 520050144519064503L;

	/**
	 * 内容
	 */
	private String content;

	public Text(String content) {
		super(MediaType.text);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Text [content=" + content + "]";
	}
}
