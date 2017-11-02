package com.zone.weixin4j.response;

/**
 * 空白回复(避免微信服务器重复推送消息)
 *
 * @className BlankResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月7日
 * @since JDK 1.6
 * @see
 */
public class BlankResponse extends SingleResponse {

	public static final BlankResponse global = new BlankResponse();

	private BlankResponse() {
		super("success");
	}
}
