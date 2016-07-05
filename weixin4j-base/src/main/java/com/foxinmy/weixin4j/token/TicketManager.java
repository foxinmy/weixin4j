package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * 第三方应用ticket的存取
 *
 * @className TicketManager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月22日
 * @since JDK 1.6
 * @see
 */
public class TicketManager {

	/**
	 * 第三方ID
	 */
	private final String id;
	/**
	 * 第三方secret
	 */
	private final String secret;
	/**
	 * ticket存储策略
	 */
	private final CacheStorager<Token> cacheStorager;

	/**
	 * 
	 * @param id
	 *            第三方ID
	 * @param secret
	 *            第三方secret
	 * @param cacheStorager
	 *            ticket存储策略
	 */
	public TicketManager(String id, String secret, CacheStorager<Token> cacheStorager) {
		this.id = id;
		this.secret = secret;
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
		return String.format("%sthird_party_ticket_%s", TokenCreator.CACHEKEY_PREFIX, id);
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

	public String getId() {
		return id;
	}

	public String getSecret() {
		return secret;
	}

	public CacheStorager<Token> getCacheStorager() {
		return this.cacheStorager;
	}
}
