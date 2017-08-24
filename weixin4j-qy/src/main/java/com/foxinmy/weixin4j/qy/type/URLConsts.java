package com.foxinmy.weixin4j.qy.type;

/**
 * URL常量类
 * 
 * @className URLConsts
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年12月3日
 * @since JDK 1.6
 * @see
 */
public final class URLConsts {
	/**
	 * 
	 */
	public static final String BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin";
	/**
	 * 企业号获取token的url
	 */
	public static final String ASSESS_TOKEN_URL = BASE_URL + "/gettoken?corpid=%s&corpsecret=%s";
	/**
	 * 企业号提供商获取token的url
	 */
	public static final String PROVIDER_TOKEN_URL = BASE_URL + "/service/get_provider_token";
	/**
	 * 企业号jssdk获取token的url
	 */
	public static final String JS_TICKET_URL = BASE_URL + "/get_jsapi_ticket?access_token=%s";
	/**
	 * 应用套件oauth授权
	 */
	public static final String SUITE_OAUTH_URL = "https://open.work.weixin.qq.com/3rdapp/install?suite_id=%s&pre_auth_code=%s&redirect_uri=%s&state=%s";
	/**
	 * 企业号获取ticket的url
	 */
	public static final String SUITE_TICKET_URL = BASE_URL + "/ticket/get?access_token=%s&type=%s";
	/**
	 * 企业号第三方应用套件获取token的url
	 */
	public static final String SUITE_TOKEN_URL = BASE_URL + "/service/get_suite_token";

	/**
	 * 企业号第三方应用套件获取预授权码的url
	 */
	public static final String SUITE_PRE_CODE_URL = BASE_URL + "/service/get_pre_auth_code?suite_access_token=%s";

	/**
	 * 企业号第三方应用套件获取企业号access_token的url
	 */
	public static final String TOKEN_SUITE_URL = BASE_URL + "/service/get_corp_token?suite_access_token=%s";
}
