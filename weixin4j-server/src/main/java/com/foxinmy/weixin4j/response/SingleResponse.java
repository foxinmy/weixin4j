package com.foxinmy.weixin4j.response;

/**
 * 单一的字符串回复,如回复SUCCESS
 * 
 * @className SingleResponse
 * @author jy
 * @date 2015年6月23日
 * @since JDK 1.7
 * @see
 */
public interface SingleResponse {
	/**
	 * 回复内容
	 * 
	 * @return
	 */
	public String toContent();
}
