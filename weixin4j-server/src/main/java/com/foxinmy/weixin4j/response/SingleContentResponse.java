package com.foxinmy.weixin4j.response;

/**
 * 单一内容回复
 * 
 * @className SingleContentResponse
 * @author jy
 * @date 2015年8月3日
 * @since JDK 1.7
 * @see
 */
public class SingleContentResponse implements SingleResponse {

	private final String content;

	public SingleContentResponse(String content) {
		this.content = content;
	}
	
	@Override
	public String toContent() {
		return content;
	}
}
