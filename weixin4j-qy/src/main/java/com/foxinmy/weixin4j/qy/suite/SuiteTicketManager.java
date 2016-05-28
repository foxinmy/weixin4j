package com.foxinmy.weixin4j.qy.suite;

import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 应用套件ticket的存取
 *
 * @className SuiteTicketManager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月22日
 * @since JDK 1.6
 * @see
 */
public class SuiteTicketManager {

	private final String suiteId;
	private final String suiteSecret;
	private final CacheStorager<Token> cacheStorager;

	public SuiteTicketManager(String suiteId, String suiteSecret,
			CacheStorager<Token> cacheStorager) {
		this.suiteId = suiteId;
		this.suiteSecret = suiteSecret;
		this.cacheStorager = cacheStorager;
	}

	/**
	 * 获取ticket
	 *
	 * @return
	 * @throws WeixinException
	 */
	public String getTicket() throws WeixinException {
		return cacheStorager.lookup(getCacheKey()).getAccessToken();
	}

	/**
	 * 获取ticket的key
	 *
	 * @return
	 */
	public String getCacheKey() {
		return String.format("%sqy_suite_ticket_%s",
				TokenCreator.CACHEKEY_PREFIX, suiteId);
	}

	/**
	 * 缓存ticket
	 *
	 * @param ticket
	 * @throws WeixinException
	 */
	public void cachingTicket(String ticket) throws WeixinException {
		Token token = new Token(ticket);
		cacheStorager.caching(getCacheKey(), token);
	}

	public String getSuiteId() {
		return this.suiteId;
	}

	public String getSuiteSecret() {
		return this.suiteSecret;
	}

	public CacheStorager<Token> getCacheStorager() {
		return this.cacheStorager;
	}
}
