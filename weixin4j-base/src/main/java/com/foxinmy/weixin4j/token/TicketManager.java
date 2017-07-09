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
	private final String thirdId;
	/**
	 * 第三方secret
	 */
	private final String thirdSecret;
	/**
	 * ticket存储策略
	 */
	private final CacheStorager<Token> cacheStorager;

	/**
	 *
	 * @param thirdId
	 *            第三方ID suiteId/componentId
	 * @param thirdSecret
	 *            第三方secret
	 * @param cacheStorager
	 *            ticket存储策略
	 */
	public TicketManager(String thirdId, String thirdSecret,
			CacheStorager<Token> cacheStorager) {
		this.thirdId = thirdId;
		this.thirdSecret = thirdSecret;
		this.cacheStorager = cacheStorager;
	}

	/**
	 * 获取ticket对象
	 *
	 * @return token对象
	 * @throws WeixinException
	 */
	public Token getTicket() throws WeixinException {
		return cacheStorager.lookup(getCacheKey());
	}

	/**
	 * 获取ticket
	 *
	 * @return ticket
	 * @throws WeixinException
	 */
	public String getAccessTicket() throws WeixinException {
		return getTicket().getAccessToken();
	}

	/**
	 * 获取ticket的key
	 *
	 * @return
	 */
	public String getCacheKey() {
		return String.format("%sthird_party_ticket_%s",
				TokenCreator.CACHEKEY_PREFIX, thirdId);
	}

	/**
	 * 缓存ticket
	 *
	 * @param ticket
	 *            票据凭证
	 * @throws WeixinException
	 */
	public void cachingTicket(String ticket) throws WeixinException {
		Token token = new Token(ticket);
		cacheStorager.caching(getCacheKey(), token);
	}

	public String getThirdId() {
		return thirdId;
	}

	public String getThirdSecret() {
		return thirdSecret;
	}

	public CacheStorager<Token> getCacheStorager() {
		return cacheStorager;
	}
}
