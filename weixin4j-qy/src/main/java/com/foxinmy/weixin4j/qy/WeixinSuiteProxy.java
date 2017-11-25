package com.foxinmy.weixin4j.qy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.qy.api.ProviderApi;
import com.foxinmy.weixin4j.qy.api.SuiteApi;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.qy.model.WeixinQyAccount;
import com.foxinmy.weixin4j.qy.token.WeixinProviderTokenCreator;
import com.foxinmy.weixin4j.qy.type.LoginTargetType;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TicketManager;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信第三方应用接口实现
 *
 * @className WeixinSuiteProxy
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月22日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
 * @see <a href= "http://work.weixin.qq.com/api/doc#10968"> 企业号第三方应用</a>
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
	 * 企业号账号信息
	 */
	private final WeixinQyAccount weixinQyAccount;

	/**
	 * 微信第三方套件接口实现(使用weixin4j.properties配置的account账号信息,
	 * 使用FileCacheStorager文件方式缓存TOKEN)
	 */
	public WeixinSuiteProxy() {
		this(new FileCacheStorager<Token>());
	}

	/**
	 * 微信第三方套件接口实现(使用weixin4j.properties配置的account账号信息)
	 *
	 * @param cacheStorager
	 *            token管理
	 */
	public WeixinSuiteProxy(CacheStorager<Token> cacheStorager) {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinQyAccount.class), cacheStorager);
	}

	/**
	 * 微信第三方套件接口实现
	 *
	 * @param weixinQyAccount
	 *            账号信息
	 * @param cacheStorager
	 *            token管理
	 */
	public WeixinSuiteProxy(WeixinQyAccount weixinQyAccount,
			CacheStorager<Token> cacheStorager) {
		if (weixinQyAccount == null) {
			throw new IllegalArgumentException(
					"weixinQyAccount must not be empty");
		}
		if (cacheStorager == null) {
			throw new IllegalArgumentException(
					"cacheStorager must not be empty");
		}
		this.weixinQyAccount = weixinQyAccount;
		List<WeixinAccount> suites = weixinQyAccount.getSuites();
		if (suites != null && !suites.isEmpty()) {
			this.suiteMap = new HashMap<String, SuiteApi>(suites.size());
			for (WeixinAccount suite : suites) {
				this.suiteMap.put(suite.getId(), new SuiteApi(
						new TicketManager(suite.getId(), suite.getSecret(),
								cacheStorager)));
			}
			this.suiteMap.put(null, suiteMap.get(suites.get(0).getId()));
		}
		if (StringUtil.isNotBlank(weixinQyAccount.getId())
				&& StringUtil.isNotBlank(weixinQyAccount.getProviderSecret())) {
			this.providerApi = new ProviderApi(
					new TokenManager(new WeixinProviderTokenCreator(
							weixinQyAccount.getId(), weixinQyAccount
									.getProviderSecret()), cacheStorager),
					cacheStorager);
		}
	}

	/**
	 * 企业号信息
	 *
	 * @return
	 */
	public WeixinQyAccount getWeixinQyAccount() {
		return weixinQyAccount;
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
	 * 获取套件的预授权码 <font color="red">需先缓存ticket</font>
	 *
	 * @param suiteId
	 *            套件ID
	 * @return 预授权码
	 * @see #cacheSuiteTicket(String, String)
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi#getTicketManager()
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi#getPreCodeManager()
	 * @throws WeixinException
	 */
	public String getPreSuiteTicket(String suiteId) throws WeixinException {
		SuiteApi suite = suite(suiteId);
		Token token = suite.getTicketManager().getTicket();
		if (token == null || StringUtil.isBlank(token.getAccessToken())) {
			throw new WeixinException("maybe oauth first?");
		}
		return suite.getPreCodeManager().getAccessToken();
	}

	/**
	 * 缓存套件ticket
	 *
	 * @param suiteId
	 *            套件ID
	 * @param suiteTicket
	 *            套件ticket内容
	 * @see <a href= "http://work.weixin.qq.com/api/doc#10982/推送suite_ticket">
	 *      推送suite_ticket协议</a>
	 * @throws WeixinException
	 */
	public void cacheSuiteTicket(String suiteId, String suiteTicket)
			throws WeixinException {
		suite(suiteId).getTicketManager().cachingTicket(suiteTicket);
	}

	/**
	 * 应用套件授权 <font color="red">需先缓存ticket</font> <li>
	 * redirectUri默认填写weixin4j.properties#suite.oauth.redirect.uri <li>
	 * state默认填写state
	 *
	 * @param suiteId
	 *            套件ID
	 * @see {@link #getSuiteAuthorizationURL(String, String,String)}
	 * @return 请求授权的URL
	 * @throws WeixinException
	 */
	public String getSuiteAuthorizationURL(String suiteId)
			throws WeixinException {
		String redirectUri = Weixin4jConfigUtil
				.getValue("suite.oauth.redirect.uri");
		return getSuiteAuthorizationURL(suiteId, redirectUri, "state");
	}

	/**
	 * 应用套件授权 <font
	 * color="red">需先缓存ticket，在授权完成之后需要调用SuiteApi#exchangeAuthInfo方法
	 * ,否则无法缓存token相关导致后续的组件接口调用失败</font>
	 *
	 * @param suiteId
	 *            套件ID
	 * @param redirectUri
	 *            授权后重定向url
	 * @param state
	 *            回调后原样返回
	 * @see #cacheSuiteTicket(String, String)
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi#getTicketManager()
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi#getPreCodeManager()
	 * @see com.foxinmy.weixin4j.qy.api.SuiteApi#exchangeAuthInfo(String)
	 * @see <a href="http://work.weixin.qq.com/api/doc#10974">企业号第三方应用套件授权</a>
	 * @return 请求授权的URL
	 * @throws WeixinException
	 */
	public String getSuiteAuthorizationURL(String suiteId, String redirectUri,
			String state) throws WeixinException {
		try {
			return String.format(URLConsts.SUITE_OAUTH_URL, suiteId,
					getPreSuiteTicket(suiteId),
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
	 *            oauth2.0授权企业号管理员登录产生的code:通过成员授权获取到的code，每次成员授权带上的code将不一样，
	 *            code只能使用一次，5分钟未被使用自动过期
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
	public String getLoginUrl(String corpId, LoginTargetType targetType,
			int agentId) throws WeixinException {
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
		return new WeixinProxy(suite(suiteId).getPerTicketManager(authCorpId),
				suite(suiteId).getTokenManager());
	}

	public final static String VERSION = "1.7.9";
}
