package com.foxinmy.weixin4j.mp.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.mp.component.WeixinComponentPreCodeCreator;
import com.foxinmy.weixin4j.mp.component.WeixinComponentTokenCreator;
import com.foxinmy.weixin4j.mp.component.WeixinTokenComponentCreator;
import com.foxinmy.weixin4j.mp.model.AuthorizerOption;
import com.foxinmy.weixin4j.mp.model.AuthorizerOption.AuthorizerOptionName;
import com.foxinmy.weixin4j.mp.model.ComponentAuthorizer;
import com.foxinmy.weixin4j.mp.model.ComponentAuthorizerToken;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.token.PerTicketManager;
import com.foxinmy.weixin4j.token.TicketManager;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 第三方应用组件
 *
 * @className ComponentApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月17日
 * @since JDK 1.6
 * @see <a href=
 *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=&lang=zh_CN">
 *      第三方应用组件概述</a>
 */
public class ComponentApi extends MpApi {
	/**
	 * 应用套件token
	 */
	private final TokenManager tokenManager;
	/**
	 * 应用套件ticket
	 */
	private final TicketManager ticketManager;
	/**
	 * 应用套件pre_code
	 */
	private final TokenManager preCodeManager;

	/**
	 *
	 * @param ticketManager
	 *            组件ticket存取
	 */
	public ComponentApi(TicketManager ticketManager) {
		this.ticketManager = ticketManager;
		this.tokenManager = new TokenManager(new WeixinComponentTokenCreator(ticketManager),
				ticketManager.getCacheStorager());
		this.preCodeManager = new TokenManager(
				new WeixinComponentPreCodeCreator(tokenManager, ticketManager.getThirdId()),
				ticketManager.getCacheStorager());
	}

	/**
	 * 应用组件token
	 *
	 * @return 应用组件的token管理
	 */
	public TokenManager getTokenManager() {
		return this.tokenManager;
	}

	/**
	 * 应用组件ticket
	 *
	 * @return 应用组件的ticket管理
	 */
	public TicketManager getTicketManager() {
		return this.ticketManager;
	}

	/**
	 * 应用组件预授权码
	 *
	 * @return 应用组件的precode管理
	 */
	public TokenManager getPreCodeManager() {
		return this.preCodeManager;
	}

	/**
	 * 应用套组件永久刷新令牌:刷新令牌主要用于公众号第三方平台获取和刷新已授权用户的access_token，只会在授权时刻提供，请妥善保存。
	 * 一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌
	 *
	 * @param authAppId
	 *            授权方appid
	 * @return 应用组件的perticket管理
	 */
	public PerTicketManager getRefreshTokenManager(String authAppId) {
		return new PerTicketManager(authAppId, ticketManager.getThirdId(), ticketManager.getThirdSecret(),
				ticketManager.getCacheStorager());
	}

	/**
	 * 第三方组件代替授权公众号发起网页授权：获取code
	 * <li>redirectUri默认填写weixin4j.properties#component.user.oauth.redirect.uri
	 * <li>scope默认填写snsapi_base
	 * <li>state默认填写state
	 * 
	 * @param authAppId
	 *            公众号的appid
	 * @see #getAuthorizationURL(String, String, String, String)
	 * @see <a href=
	 *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318590&token=&lang=zh_CN">
	 *      第三方组件代替授权公众号发起网页授权</a>
	 */
	public String getUserAuthorizationURL(String authAppId) {
		String redirectUri = Weixin4jConfigUtil.getValue("component.user.oauth.redirect.uri");
		return getUserAuthorizationURL(authAppId, redirectUri, "snsapi_base", "state");
	}

	/**
	 * 第三方组件代替授权公众号发起网页授权：获取code
	 * 
	 * @param authAppId
	 *            公众号的appid
	 * @param redirectUri
	 *            重定向地址，这里填写的应是服务开发方的回调地址
	 * @param scope
	 *            应用授权作用域，snsapi_base/snsapi_userinfo
	 * @param state
	 *            重定向后会带上state参数，开发者可以填写任意参数值，最多128字节
	 * @return oauth授权URL
	 * @see <a href=
	 *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318590&token=&lang=zh_CN">
	 *      第三方组件代替授权公众号发起网页授权</a>
	 */
	public String getUserAuthorizationURL(String authAppId, String redirectUri, String scope, String state) {
		String sns_component_user_auth_uri = getRequestUri("sns_component_user_auth_uri");
		try {
			return String.format(sns_component_user_auth_uri, authAppId,
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()), scope, state, this.ticketManager.getThirdId());
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}

	/**
	 * 第三方组件代替授权公众号发起网页授权：换取token
	 * 
	 * @param authAppId
	 *            公众号的appid
	 * @param code
	 *            用户同意授权获取的code
	 * @return token信息
	 * @see #getUserAuthorizationURL(String, String, String, String)
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @throws WeixinException
	 */
	public OauthToken getAuthorizationToken(String authAppId, String code) throws WeixinException {
		String sns_component_user_token_uri = getRequestUri("sns_component_user_token_uri");
		String accessToken = tokenManager.getAccessToken();
		WeixinResponse response = weixinExecutor.get(
				String.format(sns_component_user_token_uri, authAppId, code, ticketManager.getThirdId(), accessToken));
		JSONObject result = response.getAsJson();
		OauthToken token = new OauthToken(result.getString("access_token"), result.getLongValue("expires_in") * 1000l);
		token.setOpenId(result.getString("openid"));
		token.setScope(result.getString("scope"));
		token.setRefreshToken(result.getString("refresh_token"));
		return token;
	}

	/**
	 * 第三方组件代替授权公众号发起网页授权：刷新token
	 * 
	 * @param authAppId
	 *            公众号的appid
	 * @param refreshToken
	 *            填写通过access_token获取到的refresh_token参数
	 * @return token信息
	 * @see #getAuthorizationToken(String, String)
	 * @see OauthApi#getAuthorizationUser(OauthToken)
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 * @throws WeixinException
	 */
	public OauthToken refreshAuthorizationToken(String authAppId, String refreshToken) throws WeixinException {
		String sns_component_token_refresh_uri = getRequestUri("sns_component_token_refresh_uri");
		String accessToken = tokenManager.getAccessToken();
		WeixinResponse response = weixinExecutor.get(String.format(sns_component_token_refresh_uri, authAppId,
				ticketManager.getThirdId(), accessToken, refreshToken));
		JSONObject result = response.getAsJson();
		OauthToken token = new OauthToken(result.getString("access_token"), result.getLongValue("expires_in") * 1000l);
		token.setOpenId(result.getString("openid"));
		token.setScope(result.getString("scope"));
		token.setRefreshToken(result.getString("refresh_token"));
		return token;
	}

	/**
	 * 使用授权码换取公众号的接口调用凭据和授权信息:用于使用授权码换取授权公众号的授权信息，
	 * 并换取authorizer_access_token和authorizer_refresh_token。
	 * 授权码的获取，需要在用户在第三方平台授权页中完成授权流程后
	 * ，在回调URI中通过URL参数提供给第三方平台方。请注意，由于现在公众号可以自定义选择部分权限授权给第三方平台
	 * ，因此第三方平台开发者需要通过该接口来获取公众号具体授权了哪些权限，而不是简单地认为自己声明的权限就是公众号授权的权限。
	 * 
	 * @param authCode
	 *            授权code
	 * @return 第三方组件授权信息
	 * @see {@link com.foxinmy.weixin4j.mp.WeixinComponentProxy#getComponentAuthorizeURL(String, String, String)}
	 * @see com.foxinmy.weixin4j.mp.model.ComponentAuthorizerToken
	 * @throws WeixinException
	 */
	public ComponentAuthorizerToken exchangeAuthorizerToken(String authCode) throws WeixinException {
		String component_exchange_authorizer_uri = getRequestUri("component_query_authorization_uri");
		JSONObject obj = new JSONObject();
		obj.put("component_appid", ticketManager.getThirdId());
		obj.put("authorization_code", authCode);
		WeixinResponse response = weixinExecutor.post(
				String.format(component_exchange_authorizer_uri, tokenManager.getAccessToken()), obj.toJSONString());
		JSONObject authObj = response.getAsJson().getJSONObject("authorization_info");
		JSONArray privilegesObj = authObj.getJSONArray("func_info");
		List<Integer> privileges = new ArrayList<Integer>(privilegesObj.size());
		for (int i = 0; i < privilegesObj.size(); i++) {
			privileges.add(privilegesObj.getJSONObject(i).getJSONObject("funcscope_category").getInteger("id"));
		}
		ComponentAuthorizerToken token = new ComponentAuthorizerToken(authObj.getString("authorizer_access_token"),
				authObj.getLongValue("expires_in") * 1000l);
		token.setRefreshToken(authObj.getString("authorizer_refresh_token"));
		token.setPrivileges(privileges);
		token.setAppId(authObj.getString("authorizer_appid"));
		// 微信授权公众号的永久刷新令牌
		PerTicketManager perTicketManager = getRefreshTokenManager(token.getAppId());
		// 缓存微信公众号的access_token
		TokenCreator tokenCreator = new WeixinTokenComponentCreator(perTicketManager, tokenManager);
		ticketManager.getCacheStorager().caching(tokenCreator.key(), token);
		// 缓存微信公众号的永久授权码(refresh_token)
		perTicketManager.cachingTicket(token.getRefreshToken());
		return token;
	}

	/**
	 * 获取授权方的公众号帐号基本信息:获取授权方的公众号基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和二维码图片URL。
	 * 需要特别记录授权方的帐号类型，在消息及事件推送时，对于不具备客服接口的公众号，需要在5秒内立即响应；而若有客服接口，则可以选择暂时不响应，
	 * 而选择后续通过客服接口来发送消息触达粉丝
	 * 
	 * @param authAppId
	 *            授权方appid
	 * @return 第三方组件授权信息
	 * @see com.foxinmy.weixin4j.mp.model.ComponentAuthorizer
	 * @throws WeixinException
	 */
	public ComponentAuthorizer getAuthorizerInfo(String authAppId) throws WeixinException {
		String component_get_authorizer_uri = getRequestUri("component_get_authorizer_uri");
		JSONObject obj = new JSONObject();
		obj.put("component_appid", ticketManager.getThirdId());
		obj.put("authorizer_appid", authAppId);
		WeixinResponse response = weixinExecutor
				.post(String.format(component_get_authorizer_uri, tokenManager.getAccessToken()), obj.toJSONString());
		obj = response.getAsJson();
		JSONObject auth = obj.getJSONObject("authorizer_info");
		ComponentAuthorizer authorizer = JSON.toJavaObject(auth, ComponentAuthorizer.class);
		authorizer.setServiceType(auth.getJSONObject("service_type_info").getIntValue("id"));
		authorizer.setVerifyType(auth.getJSONObject("verify_type_info").getIntValue("id"));
		auth = obj.getJSONObject("authorization_info");
		JSONArray privilegesObj = auth.getJSONArray("func_info");
		List<Integer> privileges = new ArrayList<Integer>(privilegesObj.size());
		for (int i = 0; i < privilegesObj.size(); i++) {
			privileges.add(privilegesObj.getJSONObject(i).getJSONObject("funcscope_category").getInteger("id"));
		}
		authorizer.setPrivileges(privileges);
		authorizer.setAppId(auth.getString("appid"));
		return authorizer;
	}

	/**
	 * 获取授权方的公众号的选项设置信息，如：地理位置上报，语音识别开关，多客服开关。注意，获取各项选项设置信息
	 * 
	 * @param authAppId
	 *            授权方appid
	 * @param option
	 *            选项名称
	 * @return 选项信息
	 * @see com.foxinmy.weixin4j.mp.model.AuthorizerOption
	 * @throws WeixinException
	 */
	public AuthorizerOption getAuthorizerOption(String authAppId, AuthorizerOptionName optionName)
			throws WeixinException {
		String component_get_authorizer_option_uri = getRequestUri("component_get_authorizer_option_uri");
		JSONObject obj = new JSONObject();
		obj.put("component_appid", ticketManager.getThirdId());
		obj.put("authorizer_appid", authAppId);
		obj.put("option_name", optionName.name());
		WeixinResponse response = weixinExecutor.post(
				String.format(component_get_authorizer_option_uri, tokenManager.getAccessToken()), obj.toJSONString());
		int optionValue = response.getAsJson().getIntValue("option_value");
		return AuthorizerOption.parse(optionName, optionValue);
	}

	/**
	 * 设置授权方的公众号的选项信息，如：地理位置上报，语音识别开关，多客服开关。注意，获取各项选项设置信息
	 * 
	 * @param option
	 *            选项信息
	 * @return 设置标识
	 * @see com.foxinmy.weixin4j.mp.model.AuthorizerOption
	 * @throws WeixinException
	 */
	public ApiResult setAuthorizerOption(String authAppId, AuthorizerOption option) throws WeixinException {
		String component_set_authorizer_option_uri = getRequestUri("component_set_authorizer_option_uri");
		JSONObject obj = new JSONObject();
		obj.put("component_appid", ticketManager.getThirdId());
		obj.put("authorizer_appid", authAppId);
		obj.put("option_name", option.getName());
		obj.put("option_value", option.getValue());
		WeixinResponse response = weixinExecutor.post(
				String.format(component_set_authorizer_option_uri, tokenManager.getAccessToken()), obj.toJSONString());
		return response.getAsResult();
	}
}
