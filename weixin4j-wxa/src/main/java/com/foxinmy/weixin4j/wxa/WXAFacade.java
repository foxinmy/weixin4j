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
import com.foxinmy.weixin4j.wxa.api.TemplateApi;
import com.foxinmy.weixin4j.wxa.api.TemplateMessageApi;

/**
 * The facade of WXA APIs.
 *
 * @since 1.8
 */
public class WXAFacade {

	private final LoginApi loginApi;
	private final QrCodeApi qrCodeApi;
	private final TemplateApi templateApi;
	private final TemplateMessageApi templateMessageApi;
	private final CustomMessageApi customMessageApi;

	/**
	 * Constructs {@link WXAFacade} using {@link FileCacheStorager}.
	 *
	 * @param weixinAccount the {@link WeixinAccount}.
	 */
	public WXAFacade(
		WeixinAccount weixinAccount
	) {
		this(
			weixinAccount,
			new FileCacheStorager<Token>()
		);
	}

	/**
	 * Constructs {@link WXAFacade} using specified {@link CacheStorager}.
	 *
	 * @param weixinAccount the {@link WeixinAccount}.
	 * @param cacheStorager the {@link CacheStorager}.
	 */
	public WXAFacade(
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
	 * Constructs {@link WXAFacade} using specified {@link CacheStorager},
	 * and overrides properties defined in {@code weixin.properties}.
	 *
	 * @param weixinAccount the {@link WeixinAccount}.
	 * @param cacheStorager the {@link CacheStorager}.
	 * @param properties properties to overrides the properties defined in {@code weixin.properties}.
	 */
	public WXAFacade(
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

	private WXAFacade(
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
	}

	public LoginApi getLoginApi() {
		return loginApi;
	}

	public QrCodeApi getQrCodeApi() {
		return qrCodeApi;
	}

	public TemplateApi getTemplateApi() {
		return templateApi;
	}

	public TemplateMessageApi getTemplateMessageApi() {
		return templateMessageApi;
	}

	public CustomMessageApi getCustomMessageApi() {
		return customMessageApi;
	}

}
