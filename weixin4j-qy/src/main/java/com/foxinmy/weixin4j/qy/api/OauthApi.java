package com.foxinmy.weixin4j.qy.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 企业号oauth授权
 *
 * @className OauthApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月11日
 * @since JDK 1.6
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=OAuth%E9%AA%8C%E8%AF%81%E6%8E%A5%E5%8F%A3">
 *      企业号用户身份授权说明</a>
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E7%99%BB%E5%BD%95%E6%8E%88%E6%9D%83">
 *      企业号第三方提供商授权说明</a>
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%88%E6%9D%83">
 *      企业号第三方套件应用授权说明</a>
 */
public class OauthApi extends QyApi {
	private final WeixinAccount account;

	/**
	 * 默认使用weixin4j.properties里面的corpid、corpsecret信息
	 */
	public OauthApi() {
		this(Weixin4jConfigUtil.getWeixinAccount());
	}

	/**
	 * 传入corpid、appsecret信息
	 *
	 * @param account
	 */
	public OauthApi(WeixinAccount account) {
		this.account = account;
	}

	/**
	 * 企业号用户身份授权:重定向URL使用weixin4j.properties#user.oauth.redirect.uri
	 *
	 * @see {@link #getUserAuthorizeURL(String,String)}
	 *
	 * @return 请求授权的URL
	 */
	public String getUserAuthorizeURL() {
		String redirectUri = Weixin4jConfigUtil
				.getValue("user.oauth.redirect.uri");
		return getUserAuthorizeURL(redirectUri, "state");
	}

	/**
	 * 企业号用户身份授权
	 *
	 * @param redirectUri
	 *            重定向地址
	 * @param state
	 *            用于保持请求和回调的状态
	 * @return 请求授权的URL
	 * @see UserApi
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinProxy#getUserByCode(String)}
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E8%BA%AB%E4%BB%BD%E9%AA%8C%E8%AF%81">
	 *      企业号用户身份授权</a>
	 */
	public String getUserAuthorizeURL(String redirectUri, String state) {
		String oauth_uri = getRequestUri("user_oauth_uri");
		try {
			return String.format(oauth_uri, account.getId(),
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()), state);
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}

	/**
	 * 企业号第三方提供商授权:重定向URL使用weixin4j.properties#third.oauth.redirect.uri
	 *
	 * @see {@link #getThirdAuthorizeURL(String,String)}
	 *
	 * @return 请求授权的URL
	 */
	public String getThirdAuthorizeURL() {
		String redirectUri = Weixin4jConfigUtil
				.getValue("third.oauth.redirect.uri");
		return getThirdAuthorizeURL(redirectUri, "state");
	}

	/**
	 * 企业号登陆授权
	 *
	 * @param corpId
	 *            企业号（提供商）的corpid
	 * @param redirectUri
	 *            重定向地址
	 * @param state
	 *            用于保持请求和回调的状态，授权请求后原样带回给第三方
	 * @return 请求授权的URL
	 * @see ProviderApi
	 * @see {@link com.foxinmy.weixin4j.qy.WeixinSuiteProxy#getOUserInfoByCode(String)}
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E6%88%90%E5%91%98%E7%99%BB%E5%BD%95%E6%8E%88%E6%9D%83">
	 *      企业号第三方提供商授权</a>
	 */
	public String getThirdAuthorizeURL(String redirectUri, String state) {
		String oauth_uri = getRequestUri("provider_oauth_uri");
		try {
			return String.format(oauth_uri, account.getId(),
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()), state);
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}
}
