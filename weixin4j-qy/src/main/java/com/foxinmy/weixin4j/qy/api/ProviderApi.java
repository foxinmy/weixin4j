package com.foxinmy.weixin4j.qy.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.qy.type.LoginTargetType;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.TokenStorager;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 服务商相关API
 * 
 * @className ProviderApi
 * @author jy
 * @date 2015年12月30日
 * @since JDK 1.6
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%8F%B7%E7%99%BB%E5%BD%95%E6%8E%88%E6%9D%83">企业号登录授权说明</a>
 */
public class ProviderApi extends QyApi {
	private final TokenHolder providerTokenHolder;
	private final TokenStorager tokenStorager;

	public ProviderApi(TokenHolder providerTokenHolder,
			TokenStorager tokenStorager) {
		this.providerTokenHolder = providerTokenHolder;
		this.tokenStorager = tokenStorager;
	}

	/**
	 * 第三方套件获取企业号管理员登录信息
	 * 
	 * @param authCode
	 *            oauth2.0授权企业号管理员登录产生的code
	 * @return 登陆信息
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%91%98%E7%99%BB%E5%BD%95%E4%BF%A1%E6%81%AF">授权获取企业号管理员登录信息</a>
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @throws WeixinException
	 */
	public OUserInfo getOUserInfoByCode(String authCode) throws WeixinException {
		String oauth_logininfo_uri = getRequestUri("oauth_logininfo_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(oauth_logininfo_uri,
						providerTokenHolder.getAccessToken()),
				String.format("{\"auth_code\":\"%s\"}", authCode));
		JSONObject obj = response.getAsJson();
		OUserInfo oUser = JSON.toJavaObject(obj, OUserInfo.class);
		oUser.getRedirectLoginInfo().setAccessToken(
				obj.getJSONObject("redirect_login_info").getString(
						"login_ticket"));
		tokenStorager.caching(getLoginTicketCacheKey(oUser.getCorpInfo()
				.getCorpId()), oUser.getRedirectLoginInfo());
		return oUser;
	}

	private String getLoginTicketCacheKey(String coprId) {
		return String.format("weixin4j_qy_provider_ticket_%s", coprId);
	}

	/**
	 * 获取登录企业号官网的url
	 * 
	 * @param corpId
	 *            <font color="red">oauth授权的corpid</font>
	 * @param targetType
	 *            登录跳转到企业号后台的目标页面
	 * @param agentId
	 *            授权方应用id 小余1时则不传递
	 * @return 登陆URL
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%99%BB%E5%BD%95%E4%BC%81%E4%B8%9A%E5%8F%B7%E5%AE%98%E7%BD%91%E7%9A%84url">获取登录企业号官网的url</a>
	 * @throws WeixinException
	 */
	public String getLoginUrl(String corpId, LoginTargetType targetType,
			int agentId) throws WeixinException {
		Token token = tokenStorager.lookup(getLoginTicketCacheKey(corpId));
		if (token == null || StringUtil.isBlank(token.getAccessToken())) {
			throw new WeixinException("maybe oauth first?");
		}
		String oauth_loginurl_uri = getRequestUri("oauth_loginurl_uri");
		JSONObject obj = new JSONObject();
		obj.put("login_ticket", token.getAccessToken());
		obj.put("target", targetType.name());
		if (agentId > 0) {
			obj.put("agentid", agentId);
		}
		WeixinResponse response = weixinExecutor.post(
				String.format(oauth_loginurl_uri,
						providerTokenHolder.getAccessToken()),
				obj.toJSONString());
		return response.getAsJson().getString("login_url");
	}
}
