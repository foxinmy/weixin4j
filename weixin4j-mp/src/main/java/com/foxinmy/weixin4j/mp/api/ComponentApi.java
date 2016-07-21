package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.message.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.component.WeixinComponentPreCodeCreator;
import com.foxinmy.weixin4j.mp.component.WeixinComponentTokenCreator;
import com.foxinmy.weixin4j.mp.component.WeixinTokenComponentCreator;
import com.foxinmy.weixin4j.mp.model.AuthorizerOption;
import com.foxinmy.weixin4j.mp.model.AuthorizerOption.AuthorizerOptionName;
import com.foxinmy.weixin4j.mp.model.ComponentAuthInfo;
import com.foxinmy.weixin4j.token.PerTicketManager;
import com.foxinmy.weixin4j.token.TicketManager;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 第三方应用组件
 *
 * @className ComponentApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月17日
 * @since JDK 1.6
 * @see <a href=
 *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=&lang=zh_CN">第三方应用组件概述</a>
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
		this.tokenManager = new TokenManager(new WeixinComponentTokenCreator(
				ticketManager), ticketManager.getCacheStorager());
		this.preCodeManager = new TokenManager(
				new WeixinComponentPreCodeCreator(tokenManager,
						ticketManager.getThirdId()),
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
		return new PerTicketManager(authAppId, ticketManager.getThirdId(),
				ticketManager.getThirdSecret(),
				ticketManager.getCacheStorager());
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
	 * @see com.foxinmy.weixin4j.mp.model.ComponentAuthInfo
	 * @throws WeixinException
	 */
	public ComponentAuthInfo exchangeAuthInfo(String authCode)
			throws WeixinException {
		String component_exchange_authorizer_uri = getRequestUri("component_exchange_authorizer_uri");
		JSONObject obj = new JSONObject();
		obj.put("component_appid", ticketManager.getThirdId());
		obj.put("authorization_code", authCode);
		WeixinResponse response = weixinExecutor.post(
				String.format(component_exchange_authorizer_uri,
						tokenManager.getAccessToken()), obj.toJSONString());
		obj = response.getAsJson();
		JSONObject authObj = obj.getJSONObject("authorization_info");
		JSONArray privilegesObj = authObj.getJSONArray("func_info");
		List<Integer> privileges = new ArrayList<Integer>(privilegesObj.size());
		for (int i = 0; i < privilegesObj.size(); i++) {
			privileges.add(privilegesObj.getJSONObject(i)
					.getJSONObject("funcscope_category").getInteger("id"));
		}
		ComponentAuthInfo info = new ComponentAuthInfo();
		info.setPrivileges(privileges);
		info.setAppId(authObj.getString("authorizer_appid"));
		// 微信授权公众号的永久刷新令牌
		PerTicketManager perTicketManager = getRefreshTokenManager(info
				.getAppId());
		// 缓存微信公众号的access_token
		TokenCreator tokenCreator = new WeixinTokenComponentCreator(
				perTicketManager, tokenManager);
		Token token = new Token(authObj.getString("authorizer_access_token"),
				authObj.getLongValue("expires_in") * 1000l);
		ticketManager.getCacheStorager().caching(tokenCreator.key(), token);
		// 缓存微信公众号的永久授权码(refresh_token)
		perTicketManager.cachingTicket(authObj
				.getString("authorizer_refresh_token"));
		return info;
	}

	/**
	 * 获取授权方的公众号帐号基本信息:获取授权方的公众号基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和二维码图片URL。
	 * 需要特别记录授权方的帐号类型，在消息及事件推送时，对于不具备客服接口的公众号，需要在5秒内立即响应；而若有客服接口，则可以选择暂时不响应，
	 * 而选择后续通过客服接口来发送消息触达粉丝
	 * 
	 * @param authAppId
	 *            授权方appid
	 * @return 第三方组件授权信息
	 * @see com.foxinmy.weixin4j.mp.model.ComponentAuthInfo
	 * @throws WeixinException
	 */
	public ComponentAuthInfo getAuthInfo(String authAppId)
			throws WeixinException {
		String component_get_authorizer_uri = getRequestUri("component_get_authorizer_uri");
		JSONObject obj = new JSONObject();
		obj.put("component_appid", ticketManager.getThirdId());
		obj.put("authorizer_appid", authAppId);
		WeixinResponse response = weixinExecutor.post(
				String.format(component_get_authorizer_uri,
						tokenManager.getAccessToken()), obj.toJSONString());
		obj = response.getAsJson().getJSONObject("authorizer_info");
		ComponentAuthInfo info = JSON
				.toJavaObject(obj, ComponentAuthInfo.class);
		info.setServiceType(obj.getJSONObject("service_type_info").getIntValue(
				"id"));
		info.setVerifyType(obj.getJSONObject("verify_type_info").getIntValue(
				"id"));
		JSONArray privilegesObj = obj.getJSONObject("authorization_info")
				.getJSONArray("func_info");
		List<Integer> privileges = new ArrayList<Integer>(privilegesObj.size());
		for (int i = 0; i < privilegesObj.size(); i++) {
			privileges.add(privilegesObj.getJSONObject(i)
					.getJSONObject("funcscope_category").getInteger("id"));
		}
		info.setPrivileges(privileges);
		info.setAppId(authAppId);
		return info;
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
	public AuthorizerOption getAuthorizerOption(String authAppId,
			AuthorizerOptionName optionName) throws WeixinException {
		String component_get_authorizer_option_uri = getRequestUri("component_get_authorizer_option_uri");
		JSONObject obj = new JSONObject();
		obj.put("component_appid", ticketManager.getThirdId());
		obj.put("authorizer_appid", authAppId);
		obj.put("option_name", optionName.name());
		WeixinResponse response = weixinExecutor.post(
				String.format(component_get_authorizer_option_uri,
						tokenManager.getAccessToken()), obj.toJSONString());
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
	public ApiResult setAuthorizerOption(String authAppId,
			AuthorizerOption option) throws WeixinException {
		String component_set_authorizer_option_uri = getRequestUri("component_set_authorizer_option_uri");
		JSONObject obj = new JSONObject();
		obj.put("component_appid", ticketManager.getThirdId());
		obj.put("authorizer_appid", authAppId);
		obj.put("option_name", option.getName());
		obj.put("option_value", option.getValue());
		WeixinResponse response = weixinExecutor.post(
				String.format(component_set_authorizer_option_uri,
						tokenManager.getAccessToken()), obj.toJSONString());
		return response.getAsResult();
	}
}
