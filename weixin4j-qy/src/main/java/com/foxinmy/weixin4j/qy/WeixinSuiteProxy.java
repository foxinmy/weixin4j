package com.foxinmy.weixin4j.qy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.qy.api.ProviderApi;
import com.foxinmy.weixin4j.qy.api.SuiteApi;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.qy.model.WeixinQyAccount;
import com.foxinmy.weixin4j.qy.suite.SuiteTicketHolder;
import com.foxinmy.weixin4j.qy.suite.Weixin4jSuiteSettings;
import com.foxinmy.weixin4j.qy.token.WeixinProviderTokenCreator;
import com.foxinmy.weixin4j.qy.type.LoginTargetType;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信第三方应用接口实现
 * 
 * @className WeixinSuiteProxy
 * @author jy
 * @date 2015年6月22日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%88%E6%9D%83">
 *      企业号第三方应用</a>
 */
public class WeixinSuiteProxy {

	/**
	 * 每个套件授权不一样 suiteId - suiteApi
	 */
	private Map<String, SuiteApi> suiteMap;
	/**
	 * 供应商API:如登陆URL
	 */
	private ProviderApi providerApi;
	/**
	 * 配置相关
	 */
	private final Weixin4jSuiteSettings suiteSettings;

	public WeixinSuiteProxy() {
		this(new Weixin4jSuiteSettings());
	}

	/**
	 * 
	 * @param suiteSettings
	 *            套件信息配置
	 */
	public WeixinSuiteProxy(Weixin4jSuiteSettings suiteSettings) {
		this.suiteSettings = suiteSettings;
		if (suiteSettings.getWeixinAccount().getSuiteAccounts() != null) {
			this.suiteMap = new HashMap<String, SuiteApi>();
			for (WeixinAccount suite : suiteSettings.getWeixinAccount().getSuiteAccounts()) {
				this.suiteMap.put(suite.getId(), new SuiteApi(
						new SuiteTicketHolder(suite.getId(), suite.getSecret(), suiteSettings.getTokenStorager0())));
				this.suiteMap.put(null,
						suiteMap.get(suiteSettings.getWeixinAccount().getSuiteAccounts().get(0).getId()));
			}
		}
		if (StringUtil.isNotBlank(suiteSettings.getWeixinAccount().getId())
				&& StringUtil.isNotBlank(suiteSettings.getWeixinAccount().getProviderSecret())) {
			this.providerApi = new ProviderApi(
					new TokenHolder(
							new WeixinProviderTokenCreator(suiteSettings.getWeixinAccount().getId(),
									suiteSettings.getWeixinAccount().getProviderSecret()),
							suiteSettings.getTokenStorager0()),
					suiteSettings.getTokenStorager0());
		}
	}

	/**
	 * 企业号信息
	 * 
	 * @return
	 */
	public WeixinQyAccount getWeixinAccount() {
		return this.suiteSettings.getWeixinAccount();
	}

	/**
	 * 获取套件接口对象(只关注第一个套件
	 * 
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
	 * @return API实例
	 */
	public SuiteApi suite() {
		return this.suiteMap.get(null);
	}

	/**
	 * 获取套件接口对象(多个套件
	 * 
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
	 * @param suiteId
	 *            套件ID
	 * @return API实例
	 */
	public SuiteApi suite(String suiteId) {
		return this.suiteMap.get(suiteId);
	}

	/**
	 * 缓存套件ticket(多个套件
	 * 
	 * @param suiteId
	 *            套件ID
	 * @param suiteTicket
	 *            套件ticket内容
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%9B%9E%E8%B0%83%E5%8D%8F%E8%AE%AE#.E6.8E.A8.E9.80.81suite_ticket.E5.8D.8F.E8.AE.AE">
	 *      推送suite_ticket协议</a>
	 * @throws WeixinException
	 */
	public void cacheTicket(String suiteId, String suiteTicket) throws WeixinException {
		suite(suiteId).getTicketHolder().cachingTicket(suiteTicket);
	}

	/**
	 * 应用套件授权 <font color="red">需先缓存ticket</font>
	 * 
	 * @see {@link #getSuiteAuthorizeURL(String, String,String)}
	 * @param suiteId
	 *            套件ID
	 * @see {@link #cacheTicket(String, String)}
	 * @return 请求授权的URL
	 * @throws WeixinException
	 */
	public String getSuiteAuthorizeURL(String suiteId) throws WeixinException {
		String redirectUri = Weixin4jConfigUtil.getValue("suite.oauth.redirect.uri");
		return getSuiteAuthorizeURL(suiteId, redirectUri, "state");
	}

	/**
	 * 应用套件授权 <font color="red">需先缓存ticket</font>
	 * 
	 * @param suiteId
	 *            套件ID
	 * @param redirectUri
	 *            授权后重定向url
	 * @param state
	 *            回调后原样返回
	 * @see <a href="http://qydev.weixin.qq.com/wiki/index.php?title
	 *      =%E4%BC%81%E4%B8%9A%E5%8F%B7%E7%AE%A1%E7%90%86%E5%91%98%E6%
	 *      8E%88%E6%9D%83%E5%BA%94%E7%94%A8">企业号第三方应用套件授权</a>
	 * @see {@link SuiteApi#getPreCodeHolder}
	 * @return 请求授权的URL
	 * @throws WeixinException
	 */
	public String getSuiteAuthorizeURL(String suiteId, String redirectUri, String state) throws WeixinException {
		try {
			return String.format(URLConsts.SUITE_OAUTH_URL, suiteId, suite(suiteId).getTicketHolder().getTicket(),
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()), state);
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}

	/**
	 * 第三方套件获取企业号管理员登录信息
	 * 
	 * @param authCode
	 *            oauth2.0授权企业号管理员登录产生的code
	 * @return 登陆信息
	 * @see com.foxinmy.weixin4j.qy.api.ProviderApi
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%91%98%E7%99%BB%E5%BD%95%E4%BF%A1%E6%81%AF">
	 *      授权获取企业号管理员登录信息</a>
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @throws WeixinException
	 */
	public OUserInfo getOUserInfoByCode(String authCode) throws WeixinException {
		return providerApi.getOUserInfoByCode(authCode);
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
	 * @see com.foxinmy.weixin4j.qy.api.ProviderApi
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%99%BB%E5%BD%95%E4%BC%81%E4%B8%9A%E5%8F%B7%E5%AE%98%E7%BD%91%E7%9A%84url">
	 *      获取登录企业号官网的url</a>
	 * @throws WeixinException
	 */
	public String getLoginUrl(String corpId, LoginTargetType targetType, int agentId) throws WeixinException {
		return providerApi.getLoginUrl(corpId, targetType, agentId);
	}

	/**
	 * 创建WeixinProxy对象
	 * 
	 * @param suiteId
	 *            套件ID
	 * @param authCorpId
	 *            已授权的corpid
	 * @see com.foxinmy.weixin4j.qy.WeixinProxy
	 * @return
	 */
	public WeixinProxy getWeixinProxy(String suiteId, String authCorpId) {
		return new WeixinProxy(suite(suiteId).getPerCodeHolder(authCorpId), suite(suiteId).getSuiteTokenHolder());
	}

	public final static String VERSION = "1.6.7";
}
