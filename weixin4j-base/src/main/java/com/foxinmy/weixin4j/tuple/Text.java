package com.foxinmy.weixin4j.tuple;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 文本对象
 * <p>
 * <font color="red">可用于「客服消息」「群发消息」及企业号的「聊天消息」</font>
 * </p>
 * 
 * @className Text
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月29日
 * @since JDK 1.6
 * @see
 */
public class Text implements MassTuple, NotifyTuple, ChatTuple {

	private static final long serialVersionUID = 520050144519064503L;

	@Override
	public String getMessageType() {
		return "text";
	}

	/**
	 * 内容
	 */
	private String content;

	@JSONCreator
	public Text(@JSONField(name = "content") String content) {
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
