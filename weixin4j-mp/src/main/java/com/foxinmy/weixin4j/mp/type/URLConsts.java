package com.foxinmy.weixin4j.mp.type;


/**
 * URL常量类
 * 
 * @className URLConsts
 * @author jy
 * @date 2014年12月3日
 * @since JDK 1.7
 * @see
 */
public final class URLConsts {
	/**
	 * 公众平台获取token的url
	 */
	public static final String ASSESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	/**
	 * 公众平台jssdk获取token的url
	 */
	public static final String JS_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
}
