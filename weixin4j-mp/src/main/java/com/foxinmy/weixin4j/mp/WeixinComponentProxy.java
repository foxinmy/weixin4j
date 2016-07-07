package com.foxinmy.weixin4j.mp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.api.ComponentApi;
import com.foxinmy.weixin4j.mp.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.type.URLConsts;
import com.foxinmy.weixin4j.setting.Weixin4jSettings;
import com.foxinmy.weixin4j.token.TicketManager;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信第三方应用接口实现
 *
 * @className WeixinComponentProxy
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月5日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
 * @see <a href=
 *      "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318292&token=&lang=zh_CN">
 *      公众号第三方应用</a>
 */
public class WeixinComponentProxy {

	/**
	 * 每个组件授权不一样 componentId - componentApi
	 */
	private Map<String, ComponentApi> componentMap;
	/**
	 * 配置相关
	 */
	private final Weixin4jSettings<WeixinMpAccount> settings;

	/**
	 * 默认使用文件方式保存token、使用weixin4j.properties配置的账号信息
	 */
	public WeixinComponentProxy() {
		this(new Weixin4jSettings<WeixinMpAccount>(
				JSON.parseObject(Weixin4jConfigUtil.getValue("account"), WeixinMpAccount.class)));
	}

	/**
	 *
	 * @param settings
	 *            配置信息
	 */
	public WeixinComponentProxy(Weixin4jSettings<WeixinMpAccount> settings) {
		this.settings = settings;
		List<WeixinAccount> components = settings.getAccount().getComponents();
		this.componentMap = new HashMap<String, ComponentApi>(components.size());
		for (WeixinAccount component : components) {
			this.componentMap.put(component.getId(), new ComponentApi(
					new TicketManager(component.getId(), component.getSecret(), settings.getCacheStorager0())));
		}
		this.componentMap.put(null, componentMap.get(components.get(0).getId()));
	}

	/**
	 * 公众号信息
	 *
	 * @return
	 */
	public WeixinMpAccount getWeixinAccount() {
		return this.settings.getAccount();
	}

	/**
	 * 获取组接口对象(只关注第一个组件
	 *
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @return API实例
	 */
	public ComponentApi component() {
		return this.componentMap.get(null);
	}

	/**
	 * 获取套件接口对象(多个组件
	 *
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @param componentId
	 *            组件ID
	 * @return API实例
	 */
	public ComponentApi component(String componentId) {
		return this.componentMap.get(componentId);
	}

	/**
	 * 获取组件的预授权码 <font color="red">需先缓存ticket</font>
	 * 
	 * @param componentId
	 *            组件ID
	 * @return 预授权码
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi#getTicketManager()
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi#getPreCodeManager()
	 * @throws WeixinException
	 */
	public String getPreComponentTicket(String componentId) throws WeixinException {
		Token token = component(componentId).getTicketManager().getTicket();
		if (token == null || StringUtil.isBlank(token.getAccessToken())) {
			throw new WeixinException("maybe oauth first?");
		}
		return token.getAccessToken();
	}

	/**
	 * 缓存组件ticket
	 *
	 * @param componentId
	 *            组件ID
	 * @param componentTicket
	 *            组件ticket内容
	 * @throws WeixinException
	 */
	public void cacheTicket(String componentId, String componentTicket) throws WeixinException {
		component(componentId).getTicketManager().cachingTicket(componentTicket);
	}

	/**
	 * 应用组件授权 <font color="red">需先缓存ticket</font>
	 *
	 * @see {@link #getComponentAuthorizeURL(String, String,String)}
	 * @param componentId
	 *            组件ID
	 * @see {@link #cacheTicket(String, String)}
	 * @return 请求授权的URL
	 * @throws WeixinException
	 */
	public String getComponentAuthorizeURL(String componentId) throws WeixinException {
		String redirectUri = Weixin4jConfigUtil.getValue("component.oauth.redirect.uri");
		return getComponentAuthorizeURL(componentId, redirectUri, "state");
	}

	/**
	 * 应用组件授权 <font color="red">需先缓存ticket</font>
	 *
	 * @param componentId
	 *            组件ID
	 * @param redirectUri
	 *            授权后重定向url
	 * @param state
	 *            回调后原样返回
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi#getTicketManager()
	 * @see com.foxinmy.weixin4j.mp.api.ComponentApi#getPreCodeManager()
	 * @return 请求授权的URL
	 * @throws WeixinException
	 */
	public String getComponentAuthorizeURL(String componentId, String redirectUri, String state)
			throws WeixinException {
		try {
			return String.format(URLConsts.COMPONENT_OAUTH_URL, componentId, getPreComponentTicket(componentId),
					URLEncoder.encode(redirectUri, Consts.UTF_8.name()), state);
		} catch (UnsupportedEncodingException e) {
			;
		}
		return "";
	}

	/**
	 * 创建WeixinProxy对象
	 *
	 * @param componentId
	 *            组件ID
	 * @param authAppId
	 *            已授权的appid
	 * @see com.foxinmy.weixin4j.mp.WeixinProxy
	 * @return
	 */
	public WeixinProxy getWeixinProxy(String componentId, String authAppId) {
		return new WeixinProxy(component(componentId).getRefreshTokenManager(authAppId),
				component(componentId).getTokenManager());
	}

	public final static String VERSION = "1.7.0";
}
