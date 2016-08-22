package com.foxinmy.weixin4j.mp.type;

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
	public static final String BASE_URL = "https://api.weixin.qq.com/cgi-bin";
	/**
	 * 公众平台获取token的url
	 */
	public static final String ASSESS_TOKEN_URL = BASE_URL
			+ "/token?grant_type=client_credential&appid=%s&secret=%s";
	/**
	 * 公众平台jssdk获取token的url
	 */
	public static final String JS_TICKET_URL = BASE_URL
			+ "/ticket/getticket?access_token=%s&type=%s";
	/**
	 * 开放平台获取token的url
	 */
	public static final String COMPONENT_TOKEN_URL = BASE_URL
			+ "/component/api_component_token";
	/**
	 * 开放平台获取预授权码的url
	 */
	public static final String COMPONENET_PRE_CODE_URL = BASE_URL
			+ "/component/api_create_preauthcode?component_access_token=%s";
	/**
	 * 开放平台获取公众号access_token的url
	 */
	public static final String TOKEN_COMPONENT_URL = BASE_URL
			+ "/component/api_authorizer_token?component_access_token=%s";
	/**
	 * 开放平台oauth授权的url
	 */
	public static final String COMPONENT_OAUTH_URL = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=%s&pre_auth_code=%s&redirect_uri=%s";
}
