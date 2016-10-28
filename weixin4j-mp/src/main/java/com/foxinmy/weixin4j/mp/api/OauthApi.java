package com.foxinmy.weixin4j.mp.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.mp.type.Lang;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * oauth授权
 *
 * @className OauthApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月6日
 * @since JDK 1.6
 */
public class OauthApi extends MpApi {

	private final WeixinAccount account;

	/**
	 * 默认使用weixin4j.properties里面的appid、appsecret信息
	 */
	public OauthApi() {
		this(Weixin4jConfigUtil.getWeixinAccount());
	}

	/**
	 * 传入appid、appsecret信息
	 *
	 * @param account
	 */
	public OauthApi(WeixinAccount account) {
		this.account = account;
	}

	/**
	 * 公众号网页获取用户资料oauth授权：请求code<li>
	 * redirectUri默认填写weixin4j.properties#user.oauth.redirect.uri <li>
	 * scope默认填写snsapi_base <li>
	 * state默认填写state
	 *
	 * @see {@link #getUserAuthorizationURL(String, String,String)}
	 *
	 * @return 请求授权的URL
	 */
	public String getUserAuthorizationURL() {
		String redirectUri = Weixin4jConfigUtil
				.getValue("user.oauth.redirect.uri");
		return getUserAuthorizationURL(redirectUri, "state", "snsapi_base");
	}

	/**
	 * 公众号网页获取用户资料oauth授权：请求code
	 *
	 * @param redirectUri
	 *            重定向地址<br>
	 *            1、在微信公众号请求用户网页授权之前，开发者需要先到公众平台官网中的开发者中心页配置授权回调域名。请注意，
	 *            这里填写的是域名（是一个字符串），而不是URL，因此请勿加 http:// 等协议头；<br>
	 *            2、授权回调域名配置规范为全域名，比如需要网页授权的域名为
	 *            ：www.qq.com，配置以后此域名下面的页面http://www.qq.com/music.html 、
	 *            http://www.qq.com/login.html<br>
	 *            都可以进行OAuth2.0鉴权。但http://pay.qq.com 、 http://music.qq.com 、
	 *            http://qq.com无法进行OAuth2.0鉴权<br>
	 *            3、如果公众号登录授权给了第三方开发者来进行管理，则不必做任何设置，由第三方代替公众号实现网页授权即可
	 * @param state
	 *            用于保持请求和回调的状态，授权请求后原样带回给第三方
	 * @param scope
	 *            应用授权作用域，snsapi_base
	 *            （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
	 *            （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）<br>
	 *            1、 以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，
	 *            并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（往往是业务页面）<br>
	 *            2、以snsapi_userinfo为scope发起的网页授权
	 *            ，是用来获取用户的基本信息的。但这种授权需要用户手动同意，并且由于用户同意过
	 *            ，所以无须关注，就可在授权后获取该用户的基本信息。<br>
	 *            3、用户管理类接口中的“获取用户基本信息接口”，是在用户和公众号产生消息交互或关注后事件推送后
	 *            ，才能根据用户OpenID来获取用户基本信息
	 *            ,这个接口，包括其他微信接口，都是需要该用户（即openid）关注了公众号后，才能调用成功的。<br>
	 * @return 请求授权的URL
	 */
	public String getUserAuthorizationURL(String redirectUri, String state,
			String scope) {
		String sns_user_auth_uri = getRequestUri("sns_user_auth_uri");
		try {
			return String.format(sns_user_auth_uri, account.getId(),
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()), scope,
					state);
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}

	/**
	 * 公众号网页获取用户资料oauth授权：code换取token
	 *
	 * @param code
	 *            用户同意授权获取的code，
	 *            code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次
	 *            ，5分钟未被使用自动过期。
	 * @return oauthtoken信息
	 * @see #getUserAuthorizationURL(String, String,String)
	 * @see #getAuthorizationUser(OauthToken)
	 */
	public OauthToken getAuthorizationToken(String code) throws WeixinException {
		String user_token_uri = getRequestUri("sns_user_token_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				user_token_uri, account.getId(), account.getSecret(), code));
		JSONObject result = response.getAsJson();
		OauthToken token = new OauthToken(result.getString("access_token"),
				result.getLongValue("expires_in") * 1000l);
		token.setUnionId(result.getString("unionid"));
		token.setOpenId(result.getString("openid"));
		token.setScope(result.getString("scope"));
		token.setRefreshToken(result.getString("refresh_token"));
		return token;
	}

	/**
	 * 公众号网页获取用户资料oauth授权：刷新token，由于access_token拥有较短的有效期，当access_token超时后，
	 * 可以使用refresh_token进行刷新， refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
	 *
	 *
	 * @param refreshToken
	 *            填写通过access_token获取到的refresh_token参数
	 * @see {@link #getAuthorizationToken(String)}
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @return oauthtoken信息
	 */
	public OauthToken refreshAuthorizationToken(String refreshToken)
			throws WeixinException {
		String sns_token_refresh_uri = getRequestUri("sns_token_refresh_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				sns_token_refresh_uri, account.getId(), refreshToken));
		JSONObject result = response.getAsJson();
		OauthToken token = new OauthToken(result.getString("access_token"),
				result.getLongValue("expires_in") * 1000l);
		token.setUnionId(result.getString("unionid"));
		token.setOpenId(result.getString("openid"));
		token.setScope(result.getString("scope"));
		token.setRefreshToken(result.getString("refresh_token"));
		return token;
	}

	/**
	 * 验证access_token是否正确
	 *
	 * @param oauthToken
	 *            接口调用凭证
	 * @param openId
	 *            用户标识
	 * @return 验证结果
	 */
	public boolean verifyAuthorizationToken(String oauthToken, String openId) {
		String sns_auth_token_uri = getRequestUri("sns_auth_token_uri");
		try {
			weixinExecutor.get(String.format(sns_auth_token_uri, oauthToken,
					openId));
			return true;
		} catch (WeixinException e) {
			;
		}
		return false;
	}

	/**
	 * oauth授权获取用户信息(需scope为 snsapi_userinfo)
	 *
	 * @param token
	 *            授权信息(token&openid)
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN">授权获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @see {@link #getAuthorizationUser(String,Sring,Lang)}
	 */
	public User getAuthorizationUser(OauthToken token) throws WeixinException {
		return getAuthorizationUser(token.getAccessToken(), token.getOpenId(),
				Lang.zh_CN);
	}

	/**
	 * oauth获取用户信息(需scope为 snsapi_userinfo)
	 *
	 * @param oauthToken
	 *            授权票据
	 * @param openid
	 *            用户openid
	 * @param lang
	 *            使用语言
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN">授权获取用户信息</a>
	 * @see {@link #getAuthorizationToken(String)}
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @see com.foxinmy.weixin4j.mp.model.User
	 */
	public User getAuthorizationUser(String oauthToken, String openid, Lang lang)
			throws WeixinException {
		String user_info_uri = getRequestUri("sns_user_info_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				user_info_uri, oauthToken, openid, lang.name()));

		return response.getAsObject(new TypeReference<User>() {
		});
	}

	/**
	 * 微信开放平台oauth授权(扫码登陆)<li>
	 * redirectUri默认填写weixin4j.properties#open.user.oauth.redirect.uri <li>
	 * state默认填写state
	 *
	 * @see {@link #getOpenAuthorizationURL(String, String)}
	 * @return 请求授权的URL
	 */
	public String getOpenAuthorizationURL() {
		String redirectUri = Weixin4jConfigUtil
				.getValue("open.user.oauth.redirect.uri");
		return getOpenAuthorizationURL(redirectUri, "state");
	}

	/**
	 * 微信开放平台oauth授权(扫码登陆):请求CODE
	 *
	 * @param redirectUri
	 *            重定向地址 域名与审核时填写的授权域名一致
	 * @param state
	 *            用于保持请求和回调的状态，授权请求后原样带回给第三方
	 * @return 请求授权的URL
	 * @see <a
	 *      href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN">网站扫描登陆oauth授权</a>
	 * @see #getAuthorizationToken(String)
	 */
	public String getOpenAuthorizationURL(String redirectUri, String state) {
		String open_user_auth_uri = getRequestUri("open_user_auth_uri");
		try {
			return String.format(open_user_auth_uri, account.getId(),
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()),
					"snsapi_login", state);
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}
}
