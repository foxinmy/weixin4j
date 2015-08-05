package com.foxinmy.weixin4j.response;

/**
 * 空白回复
 * 
 * @className BlankResponse
 * @author jy
 * @date 2015年5月7日
 * @since JDK 1.7
 * @see
 */
public class BlankResponse extends SingleResponse {

	public static final BlankResponse global = new BlankResponse();

	private BlankResponse() {
		super("success");
	}
}
