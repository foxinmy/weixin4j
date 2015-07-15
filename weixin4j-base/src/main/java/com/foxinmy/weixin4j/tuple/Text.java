package com.foxinmy.weixin4j.tuple;


/**
 * 文本对象
 * <p>
 * <font color="red">可用于「客服消息」「群发消息」</font>
 * </p>
 * 
 * @className Text
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Text implements MassTuple, NotifyTuple {

	private static final long serialVersionUID = 520050144519064503L;

	@Override
	public String getMessageType() {
		return "text";
	}

	/**
	 * 内容
	 */
	private String content;

	public Text(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "Text [content=" + content + "]";
	}
}
