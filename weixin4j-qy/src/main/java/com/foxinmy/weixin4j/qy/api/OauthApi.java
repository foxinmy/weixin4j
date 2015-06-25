package com.foxinmy.weixin4j.qy.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 企业号oauth授权
 * 
 * @className OauthApi
 * @author jy
 * @date 2015年6月11日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E7%99%BB%E5%BD%95%E6%8E%88%E6%9D%83">企业号登录授权说明</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%88%E6%9D%83">第三方应用授权说明</a>
 */
public class OauthApi extends QyApi {

	/**
	 * 企业号登陆授权
	 * 
	 * @see {@link com.foxinmy.weixin4j.qy.api.OauthApi#getAuthorizeURL(String, String,String)}
	 * 
	 * @return 请求授权的URL
	 */
	public String getAuthorizeURL() {
		String corpId = DEFAULT_WEIXIN_ACCOUNT.getId();
		String redirectUri = ConfigUtil.getValue("redirect_uri");
		return getAuthorizeURL(corpId, redirectUri, "state");
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
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E7%99%BB%E5%BD%95%E6%8E%88%E6%9D%83">企业号登陆授权</a>
	 */
	public String getAuthorizeURL(String corpId, String redirectUri,
			String state) {
		String oauth_uri = getRequestUri("provider_oauth_uri");
		try {
			return String.format(oauth_uri, corpId,
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()), state);
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}
	
	/**
	 * 获取企业号管理员登录信息
	 * 
	 * @param providerToken
	 *            提供商的token
	 * @param authCode
	 *            oauth2.0授权企业号管理员登录产生的code
	 * @return 登陆信息
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%91%98%E7%99%BB%E5%BD%95%E4%BF%A1%E6%81%AF">授权获取企业号管理员登录信息</a>
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @throws WeixinException
	 */
	public OUserInfo getOUserInfoByCode(String providerToken, String authCode)
			throws WeixinException {
		String oauth_logininfo_uri = getRequestUri("oauth_logininfo_uri");
		WeixinResponse response = weixinClient.post(
				String.format(oauth_logininfo_uri, providerToken),
				String.format("{\"auth_code\":\"%s\"}", authCode));
		return JSON.parseObject(response.getAsString(), OUserInfo.class);
	}

	/**
	 * @see {@link com.foxinmy.weixin4j.qy.api.OauthApi#getSuiteAuthorizeURL(String,String, String,String)}
	 * @param preAuthCode
	 *            预授权码
	 * @return
	 */
	public String getSuiteAuthorizeURL(String preAuthCode) {
		String suiteId = DEFAULT_WEIXIN_ACCOUNT.getSuiteId();
		String redirectUri = ConfigUtil.getValue("suite_redirect_uri");
		return getSuiteAuthorizeURL(suiteId, preAuthCode, redirectUri, "state");
	}

	/**
	 * 应用套件授权
	 * 
	 * @param suiteId
	 *            套件ID
	 * @param preAuthCode
	 *            预授权码
	 * @param redirectUri
	 *            授权后重定向url
	 * @param state
	 *            回调后原样返回
	 * @return 请求授权的URL <a
	 *         href=""http://qydev.weixin.qq.com/wiki/index.php?title
	 *         =%E4%BC%81%E4%B8%9A%E5%8F%B7%E7%AE%A1%E7%90%86%E5%91%98%E6%
	 *         8E%88%E6%9D%83%E5%BA%94%E7%94%A8>应用套件授权</a>
	 */
	public String getSuiteAuthorizeURL(String suiteId, String preAuthCode,
			String redirectUri, String state) {
		String suite_oauth_uri = getRequestUri("suite_oauth_uri");
		try {
			return String.format(suite_oauth_uri, suiteId, preAuthCode,
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()), state);
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}
}
