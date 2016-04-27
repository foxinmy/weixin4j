package com.foxinmy.weixin4j.response;

import java.util.Arrays;
import java.util.List;

/**
 * 多个消息回复
 * 
 * @className MultipleResponse
 * @author jy
 * @date 2016年4月27日
 * @since JDK 1.6
 * @see
 */
public class MultipleResponse implements WeixinResponse {

	private List<WeixinResponse> responses;

	public MultipleResponse(WeixinResponse... responses) {
		if (responses == null) {
			throw new IllegalArgumentException("responses not be empty");
		}
		this.responses = Arrays.asList(responses);
	}

	@Override
	public String getMsgType() {
		return "multiple";
	}

	/**
	 * 插入首条回复
	 * 
	 * @param response
	 */
	public void addFirstResponse(WeixinResponse response) {
		responses.add(0, response);
	}

	/**
	 * 移除首条回复
	 * 
	 * @return 移除的回复
	 */
	public WeixinResponse removeFirstResponse() {
		return responses.remove(0);
	}

	/**
	 * 插入末条回复
	 * 
	 * @param response
	 */
	public void addLastResponse(WeixinResponse response) {
		responses.add(response);
	}

	/**
	 * 移除末条回复
	 * 
	 * @return 移除的回复
	 */
	public WeixinResponse removeLastResponse() {
		return responses.remove(responses.size() - 1);
	}

	/**
	 * 获取所有的回复
	 * 
	 * @return
	 */
	public List<WeixinResponse> getResponses() {
		return this.responses;
	}

	@Override
	public String toContent() {
		return null;
	}

	@Override
	public String toString() {
		return "MultipleResponse [responses=" + responses + "]";
	}
}
