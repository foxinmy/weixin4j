package com.foxinmy.weixin4j.response;

/**
 * 单一内容回复
 * 
 * @className SingleResponse
 * @author jy
 * @date 2015年8月3日
 * @since JDK 1.7
 * @see
 */
public class SingleResponse implements WeixinResponse {

	private final String content;

	public SingleResponse(String content) {
		this.content = content;
	}

	@Override
	public String toContent() {
		return content;
	}

	@Override
	public String getMsgType() {
		return "single";
	}

	@Override
	public String toString() {
		return "SingleResponse [content=" + content + "]";
	}
}
