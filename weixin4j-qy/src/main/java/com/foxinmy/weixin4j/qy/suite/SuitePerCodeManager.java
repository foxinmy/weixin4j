package com.foxinmy.weixin4j.qy.suite;

import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 应用套件永久授权码的存取
 *
 * @className SuitePerCodeManager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月22日
 * @since JDK 1.6
 * @see
 */
public class SuitePerCodeManager {

	private final String authCorpId;
	private final String suiteId;
	private final CacheStorager<Token> cacheStorager;

	public SuitePerCodeManager(String authCorpId, String suiteId,
			CacheStorager<Token> cacheStorager) {
		this.authCorpId = authCorpId;
		this.suiteId = suiteId;
		this.cacheStorager = cacheStorager;
	}

	/**
	 * 缓存永久授权码
	 *
	 * @param permanentCode
	 * @throws WeixinException
	 */
	public void cachingPermanentCode(String permanentCode)
			throws WeixinException {
		Token token = new Token(permanentCode);
		cacheStorager.caching(getCacheKey(), token);
	}

	/**
	 * 获取永久授权码的key
	 *
	 * @return
	 */
	public String getCacheKey() {
		return String.format("%sqy_suite_percode_%s_%s",
				TokenCreator.CACHEKEY_PREFIX, suiteId, authCorpId);
	}

	/**
	 * 查找永久授权码
	 *
	 * @return
	 * @throws WeixinException
	 */
	public String getPermanentCode() throws WeixinException {
		return cacheStorager.lookup(getCacheKey()).getAccessToken();
	}

	public String getSuiteId() {
		return this.suiteId;
	}

	public String getAuthCorpId() {
		return this.authCorpId;
	}

	public CacheStorager<Token> getCacheStorager() {
		return this.cacheStorager;
	}
}
