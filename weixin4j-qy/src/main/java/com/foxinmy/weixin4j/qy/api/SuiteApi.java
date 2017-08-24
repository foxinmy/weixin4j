package com.foxinmy.weixin4j.qy.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.AgentInfo;
import com.foxinmy.weixin4j.qy.model.AgentSetter;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.suite.WeixinSuitePreCodeCreator;
import com.foxinmy.weixin4j.qy.suite.WeixinSuiteTokenCreator;
import com.foxinmy.weixin4j.qy.suite.WeixinTokenSuiteCreator;
import com.foxinmy.weixin4j.token.PerTicketManager;
import com.foxinmy.weixin4j.token.TicketManager;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 第三方应用套件
 *
 * @className SuiteApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月17日
 * @since JDK 1.6
 * @see <a href="http://work.weixin.qq.com/api/doc#10968">第三方应用授权</a>
 */
public class SuiteApi extends QyApi {
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
	 *            套件ticket存取
	 */
	public SuiteApi(TicketManager ticketManager) {
		this.ticketManager = ticketManager;
		this.tokenManager = new TokenManager(new WeixinSuiteTokenCreator(
				ticketManager), ticketManager.getCacheStorager());
		this.preCodeManager = new TokenManager(new WeixinSuitePreCodeCreator(
				tokenManager, ticketManager.getThirdId()),
				ticketManager.getCacheStorager());
	}

	/**
	 * 应用套件token
	 *
	 * @return 应用套件的token管理
	 */
	public TokenManager getTokenManager() {
		return this.tokenManager;
	}

	/**
	 * 应用套件ticket
	 *
	 * @return 应用套件的ticket管理
	 */
	public TicketManager getTicketManager() {
		return this.ticketManager;
	}

	/**
	 * 应用套件预授权码
	 *
	 * @return 应用套件的precode管理
	 */
	public TokenManager getPreCodeManager() {
		return this.preCodeManager;
	}

	/**
	 * 应用套件永久授权码：企业号的永久授权码
	 *
	 * @param authCorpid
	 *            授权方corpid
	 * @return 应用套件的preticket管理
	 */
	public PerTicketManager getPerTicketManager(String authCorpId) {
		return new PerTicketManager(authCorpId, ticketManager.getThirdId(),
				ticketManager.getThirdSecret(),
				ticketManager.getCacheStorager());
	}

	/**
	 * 获取企业号access_token(永久授权码)
	 *
	 * @param authCorpid
	 *            授权方corpid
	 * @return 企业号token
	 */
	public TokenManager getPerTokenManager(String authCorpId) {
		return new TokenManager(new WeixinTokenSuiteCreator(
				getPerTicketManager(authCorpId), tokenManager),
				ticketManager.getCacheStorager());
	}

	/**
	 * 设置套件授权配置:如果需要对某次授权进行配置，则调用本接口，目前仅可以设置哪些应用可以授权，不调用则默认允许所有应用进行授权。
	 *
	 * @param appids
	 *            允许进行授权的应用id，如1、2、3
	 * @return 处理结果
	 * @throws WeixinException
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.AE.BE.E7.BD.AE.E6.8E.88.E6.9D.83.E9.85.8D.E7.BD.AE"
	 *      >设置套件授权配置</a>
	 */
	public ApiResult setSuiteSession(int... appids) throws WeixinException {
		String suite_set_session_uri = getRequestUri("suite_set_session_uri");
		JSONObject para = new JSONObject();
		para.put("pre_auth_code", preCodeManager.getAccessToken());
		JSONObject appid = new JSONObject();
		appid.put("appid", appids);
		para.put("session_info", appid);
		WeixinResponse response = weixinExecutor.post(
				String.format(suite_set_session_uri,
						tokenManager.getAccessToken()), para.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 临时授权码换取授权方的永久授权码，并换取授权信息、企业access_token
	 *
	 * @param authCode
	 *            临时授权码会在授权成功时附加在redirect_uri中跳转回应用提供商网站。
	 * @return 授权得到的信息
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @see <a href=
	 *      "https://work.weixin.qq.com/api/doc#10975/获取企业永久授权码">获取企业号的永久授权码</a>
	 */
	public OUserInfo exchangeAuthInfo(String authCode) throws WeixinException {
		String suite_get_permanent_uri = getRequestUri("suite_get_permanent_uri");
		JSONObject obj = new JSONObject();
		obj.put("suite_id", ticketManager.getThirdId());
		obj.put("auth_code", authCode);
		WeixinResponse response = weixinExecutor.post(
				String.format(suite_get_permanent_uri,
						tokenManager.getAccessToken()), obj.toJSONString());
		obj = response.getAsJson();
		obj.put("corp_info", obj.remove("auth_corp_info"));
		obj.put("user_info", obj.remove("auth_user_info"));
		OUserInfo oInfo = JSON.toJavaObject(obj, OUserInfo.class);
		// 微信授权企业号的永久授权码
		PerTicketManager perTicketManager = getPerTicketManager(oInfo
				.getCorpInfo().getCorpId());
		// 缓存微信企业号的access_token
		TokenCreator tokenCreator = new WeixinTokenSuiteCreator(
				perTicketManager, tokenManager);
		Token token = new Token(obj.getString("access_token"),
				obj.getLongValue("expires_in") * 1000l);
		ticketManager.getCacheStorager().caching(tokenCreator.key(), token);
		// 缓存微信企业号的永久授权码
		perTicketManager.cachingTicket(obj.getString("permanent_code"));
		return oInfo;
	}

	/**
	 * 获取企业号的授权信息
	 *
	 * @param authCorpId
	 *            授权方corpid
	 * @return 授权方信息
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @see <a href=
	 *      "https://work.weixin.qq.com/api/doc#10975/获取企业授权信息">获取企业号的授权信息</a>
	 */
	public OUserInfo getAuthInfo(String authCorpId) throws WeixinException {
		String suite_get_authinfo_uri = getRequestUri("suite_get_authinfo_uri");
		JSONObject obj = new JSONObject();
		obj.put("suite_id", ticketManager.getThirdId());
		obj.put("auth_corpid", authCorpId);
		obj.put("permanent_code", getPerTicketManager(authCorpId)
				.getAccessTicket());
		WeixinResponse response = weixinExecutor.post(
				String.format(suite_get_authinfo_uri,
						tokenManager.getAccessToken()), obj.toJSONString());
		obj = response.getAsJson();
		obj.put("corp_info", obj.remove("auth_corp_info"));
		obj.put("user_info", obj.remove("auth_user_info"));
		return JSON.toJavaObject(obj, OUserInfo.class);
	}

	/**
	 * 获取企业号应用
	 *
	 * @param authCorpId
	 *            授权方corpid
	 * @param agentid
	 *            授权方应用id
	 * @return 应用信息
	 * @see com.foxinmy.weixin4j.qy.model.AgentInfo
	 * @see <a href=
	 *      "https://work.weixin.qq.com/api/doc#10975/获取企业授权信息">获取企业号应用</a>
	 * @throws WeixinException
	 */
	public AgentInfo getAgent(String authCorpId, int agentid)
			throws WeixinException {
		String suite_get_agent_uri = getRequestUri("suite_get_agent_uri");
		JSONObject obj = new JSONObject();
		obj.put("suite_id", ticketManager.getThirdId());
		obj.put("auth_corpid", authCorpId);
		obj.put("permanent_code", getPerTicketManager(authCorpId)
				.getAccessTicket());
		obj.put("agentid", agentid);
		WeixinResponse response = weixinExecutor.post(
				String.format(suite_get_agent_uri,
						tokenManager.getAccessToken()), obj.toJSONString());
		JSONObject jsonObj = response.getAsJson();
		AgentInfo agent = JSON.toJavaObject(jsonObj, AgentInfo.class);
		agent.setAllowUsers(JSON.parseArray(
				jsonObj.getJSONObject("allow_userinfos").getString("user"),
				User.class));
		agent.setAllowPartys(JSON.parseArray(
				jsonObj.getJSONObject("allow_partys").getString("partyid"),
				Integer.class));
		agent.setAllowTags(JSON.parseArray(jsonObj.getJSONObject("allow_tags")
				.getString("tagid"), Integer.class));
		return agent;
	}

	/**
	 * 设置企业应用的选项设置信息，如：地理位置上报等
	 *
	 * @param authCorpId
	 *            授权方corpid
	 * @param agentSet
	 *            设置信息
	 * @see com.foxinmy.weixin4j.qy.model.AgentSetter
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E8%AE%BE%E7%BD%AE%E4%BC%81%E4%B8%9A%E5%8F%B7%E5%BA%94%E7%94%A8">设置企业号信息</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult setAgent(String authCorpId, AgentSetter agentSet)
			throws WeixinException {
		String suite_set_agent_uri = getRequestUri("suite_set_agent_uri");
		JSONObject obj = new JSONObject();
		obj.put("suite_id", ticketManager.getThirdId());
		obj.put("auth_corpid", authCorpId);
		obj.put("permanent_code", getPerTicketManager(authCorpId)
				.getAccessTicket());
		obj.put("agent", agentSet);
		WeixinResponse response = weixinExecutor.post(
				String.format(suite_set_agent_uri,
						tokenManager.getAccessToken()),
				JSON.toJSONString(obj, AgentApi.typeFilter));
		return response.getAsResult();
	}
}
