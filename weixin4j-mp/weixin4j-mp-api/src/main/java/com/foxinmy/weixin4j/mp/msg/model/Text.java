package com.foxinmy.weixin4j.mp.msg.model;

import com.foxinmy.weixin4j.type.MediaType;

/**
 * 文本对象
 * @className Text
 * @author jy
 * @date 2014年9月29日
 * @since JDK 1.7
 * @see
 */
public class Text extends BaseMsg {

	private static final long serialVersionUID = 1L;
	
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Text(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Text [content=" + content + "]";
	}

	@Override
	public MediaType getMediaType() {
		return MediaType.text;
	}
}
