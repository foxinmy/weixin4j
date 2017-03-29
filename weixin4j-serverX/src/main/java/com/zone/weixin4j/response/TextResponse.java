package com.zone.weixin4j.response;

/**
 * 回复文本消息
 * 
 * @className TextResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月5日
 * @since JDK 1.6
 * @see
 */
public class TextResponse implements WeixinResponse {

	/**
	 * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	private String content;

	public TextResponse(String content) {
		this.content = content;
	}

	@Override
	public String toContent() {
		return String.format("<Content><![CDATA[%s]]></Content>", content);
	}

	@Override
	public String getMsgType() {
		return "text";
	}

	public String getContent() {
		return content;
	}
}
