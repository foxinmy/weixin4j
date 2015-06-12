package com.foxinmy.weixin4j.qy.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.qy.model.Corpinfo;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.qy.model.WeixinQyAccount;
import com.foxinmy.weixin4j.qy.token.WeixinProviderTokenCreator;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.TokenStorager;
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
 */
public class OauthApi extends QyApi {

	private final TokenHolder providerTokenHolder;

	/**
	 * 默认使用文件方式保存token、使用weixin4j.properties配置的账号信息
	 */
	public OauthApi() {
		this(new FileTokenStorager());
	}

	/**
	 * 默认使用weixin4j.properties配置的账号信息
	 * 
	 * @param tokenStorager
	 */
	public OauthApi(TokenStorager tokenStorager) {
		this(tokenStorager, JSON.parseObject(ConfigUtil.getValue("account"),
				WeixinQyAccount.class));
	}

	/**
	 * 
	 * @param appid
	 * @param appsecret
	 */
	public OauthApi(String corpid, String providerSecret) {
		providerTokenHolder = new TokenHolder(new WeixinProviderTokenCreator(
				corpid, providerSecret), new FileTokenStorager());
	}

	/**
	 * 
	 * @param tokenStorager
	 *            token存储策略
	 * @param weixinAccount
	 *            公众号账号信息
	 */
	public OauthApi(TokenStorager tokenStorager, WeixinQyAccount qyAccount) {
		providerTokenHolder = new TokenHolder(new WeixinProviderTokenCreator(
				qyAccount), tokenStorager);
	}

	/**
	 * @see {@link com.foxinmy.weixin4j.qy.api.OauthApi#getAuthorizeURL(String, String,String)}
	 * 
	 * @return 请求授权的URL
	 */
	public String getAuthorizeURL() {
		String corpId = ConfigUtil.getWeixinAccount().getId();
		String redirectUri = ConfigUtil.getValue("redirect_uri");
		return getAuthorizeURL(corpId, redirectUri, "state");
	}

	/**
	 * oauth授权
	 * 
	 * @param corpId
	 *            企业号（提供商）的corpid
	 * @param redirectUri
	 *            重定向地址
	 * @param state
	 *            用于保持请求和回调的状态，授权请求后原样带回给第三方
	 * @return 请求授权的URL
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
	 * @param authCode
	 *            oauth2.0授权企业号管理员登录产生的code
	 * @return 登陆信息
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%91%98%E7%99%BB%E5%BD%95%E4%BF%A1%E6%81%AF">授权获取企业号管理员登录信息</a>
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @throws WeixinException
	 */
	public OUserInfo getOUserInfo(String authCode) throws WeixinException {
		String oauth_logininfo_uri = getRequestUri("oauth_logininfo_uri");
		WeixinResponse response = weixinClient.post(String.format(
				oauth_logininfo_uri, providerTokenHolder.getToken()
						.getAccessToken()), String.format(
				"{\"auth_code\":\"%s\"}", authCode));
		return JSON.parseObject(response.getAsString(), OUserInfo.class,
				new ExtraProcessor() {

					@Override
					public void processExtra(Object object, String key,
							Object value) {
						if (object instanceof Corpinfo) {
							Corpinfo corpinfo = (Corpinfo) object;
							if (key.equalsIgnoreCase("corp_name")) {
								corpinfo.setName(value.toString());
							} else if (key
									.equalsIgnoreCase("corp_round_logo_url")) {
								corpinfo.setRoundLogoUrl(value.toString());
							} else if (key
									.equalsIgnoreCase("corp_square_logo_url")) {
								corpinfo.setSquareLogoUrl(value.toString());
							}
						}
					}
				});
	}
}
