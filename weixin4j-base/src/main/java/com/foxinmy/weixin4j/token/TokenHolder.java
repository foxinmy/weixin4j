package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * 对token的缓存获取
 * 
 * @className TokenHolder
 * @author jy
 * @date 2015年6月12日
 * @since JDK 1.6
 * @see TokenCreator
 * @see TokenStorager
 */
public class TokenHolder {

	/**
	 * token的创建
	 */
	private final TokenCreator tokenCreator;
	/**
	 * token的存储
	 */
	private final TokenStorager tokenStorager;

	/**
	 * 
	 * @param tokenCreator
	 *            token创建器
	 * @param tokenStorager
	 *            token保存器
	 */
	public TokenHolder(TokenCreator tokenCreator, TokenStorager tokenStorager) {
		this.tokenCreator = tokenCreator;
		this.tokenStorager = tokenStorager;
	}

	/**
	 * 获取token对象
	 * 
	 * @return
	 * @throws WeixinException
	 */
	public Token getToken() throws WeixinException {
		String cacheKey = tokenCreator.getCacheKey();
		Token token = tokenStorager.lookup(cacheKey);
		if (token == null) {
			token = tokenCreator.createToken();
			tokenStorager.caching(cacheKey, token);
		}
		return token;
	}

	/**
	 * 获取token字符串
	 * 
	 * @return
	 * @throws WeixinException
	 */
	public String getAccessToken() throws WeixinException {
		return getToken().getAccessToken();
	}

	/**
	 * 手动刷新token
	 * 
	 * @return 刷新后的token
	 * @throws WeixinException
	 */
	public Token refreshToken() throws WeixinException {
		String cacheKey = tokenCreator.getCacheKey();
		Token token = tokenCreator.createToken();
		tokenStorager.caching(cacheKey, token);
		return token;
	}

	/**
	 * 手动移除token
	 * 
	 * @return 被移除的token
	 * @throws WeixinException
	 */
	public Token evictToken() throws WeixinException {
		String cacheKey = tokenCreator.getCacheKey();
		return tokenStorager.evict(cacheKey);
	}
}
