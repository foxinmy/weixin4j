package com.foxinmy.weixin4j.wxa;

import java.util.Properties;

import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.wxa.api.CustomMessageApi;
import com.foxinmy.weixin4j.wxa.api.LoginApi;
import com.foxinmy.weixin4j.wxa.api.QrCodeApi;
import com.foxinmy.weixin4j.wxa.api.SecCheckApi;
import com.foxinmy.weixin4j.wxa.api.TemplateApi;
import com.foxinmy.weixin4j.wxa.api.TemplateMessageApi;

/**
 * The facade of WeChat Mini Program APIs.
 *
 * @since 1.8
 */
public class WeixinAppFacade {

	private final LoginApi loginApi;
	private final QrCodeApi qrCodeApi;
	private final TemplateApi templateApi;
	private final TemplateMessageApi templateMessageApi;
	private final CustomMessageApi customMessageApi;
	private final SecCheckApi secCheckApi;

	/**
	 * Constructs {@link WeixinAppFacade} using {@link FileCacheStorager}.
	 *
	 * @param weixinAccount the {@link WeixinAccount}.
	 */
	public WeixinAppFacade(
		WeixinAccount weixinAccount
	) {
		this(
			weixinAccount,
			new FileCacheStorager<Token>()
		);
	}

	/**
	 * Constructs {@link WeixinAppFacade} using specified {@link CacheStorager}.
	 *
	 * @param weixinAccount the {@link WeixinAccount}.
	 * @param cacheStorager the {@link CacheStorager}.
	 */
	public WeixinAppFacade(
		WeixinAccount weixinAccount,
		CacheStorager<Token> cacheStorager
	) {
		this(
			weixinAccount,
			cacheStorager,
			null
		);
	}

	/**
	 * Constructs {@link WeixinAppFacade} using specified {@link CacheStorager},
	 * and overrides properties defined in {@code weixin.properties}.
	 *
	 * @param weixinAccount the {@link WeixinAccount}.
	 * @param cacheStorager the {@link CacheStorager}.
	 * @param properties properties to overrides the properties defined in {@code weixin.properties}.
	 */
	public WeixinAppFacade(
		WeixinAccount weixinAccount,
		CacheStorager<Token> cacheStorager,
		Properties properties
	) {
		this(
			weixinAccount,
			new WeixinTokenCreator(weixinAccount.getId(), weixinAccount.getSecret()),
			cacheStorager,
			properties
		);
	}

	private WeixinAppFacade(
		WeixinAccount weixinAccount,
		TokenCreator tokenCreator,
		CacheStorager<Token> cacheStorager,
		Properties properties
	) {
		if (weixinAccount == null) {
			throw new IllegalArgumentException(
				"weixinAccount must not be empty");
		}

		if (tokenCreator == null) {
			throw new IllegalArgumentException(
				"tokenCreator must not be empty");
		}

		if (cacheStorager == null) {
			throw new IllegalArgumentException(
				"cacheStorager must not be empty");
		}

		final TokenManager tokenManager = new TokenManager(tokenCreator, cacheStorager);

		this.loginApi = new LoginApi(weixinAccount, properties);
		this.qrCodeApi = new QrCodeApi(tokenManager, properties);
		this.templateApi = new TemplateApi(tokenManager, properties);
		this.templateMessageApi = new TemplateMessageApi(tokenManager, properties);
		this.customMessageApi = new CustomMessageApi(tokenManager, properties);
		this.secCheckApi = new SecCheckApi(tokenManager, properties);
	}

	/**
	 * 获取登录相关的 API。
	 *
	 * @return 登录相关 API。
	 */
	public LoginApi getLoginApi() {
		return loginApi;
	}

	/**
	 * 获取小程序码、小程序二维码相关的 API。
	 *
	 * @return 小程序码、小程序二维码相关的 API。
	 */
	public QrCodeApi getQrCodeApi() {
		return qrCodeApi;
	}

	/**
	 * 获取模版消息管理相关的 API。
	 *
	 * @return 模版消息管理相关的 API。
	 */
	public TemplateApi getTemplateApi() {
		return templateApi;
	}

	/**
	 * 获取模板消息相关的 API。
	 *
	 * @return 模板消息相关的 API。
	 */
	public TemplateMessageApi getTemplateMessageApi() {
		return templateMessageApi;
	}

	/**
	 * 获取客服消息相关的 API。
	 *
	 * @return 客服消息相关的 API。
	 */
	public CustomMessageApi getCustomMessageApi() {
		return customMessageApi;
	}

	/**
	 * 获取内容安全相关的 API。
	 *
	 * @return 内容安全相关的 API。
	 * @since 1.9
	 */
	public SecCheckApi getSecCheckApi() {
		return secCheckApi;
	}

}
