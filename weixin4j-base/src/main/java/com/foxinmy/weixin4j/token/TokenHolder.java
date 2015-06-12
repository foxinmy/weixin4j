package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * 对token的缓存获取
 * 
 * @className TokenHolder
 * @author jy
 * @date 2015年6月12日
 * @since JDK 1.7
 * @see TokenCreator
 * @see TokenStorager
 */
public final class TokenHolder {

	private final TokenCreator tokenCreator;
	private final TokenStorager tokenStorager;

	public TokenHolder(TokenCreator tokenCreator, TokenStorager tokenStorager) {
		this.tokenCreator = tokenCreator;
		this.tokenStorager = tokenStorager;
	}

	public Token getToken() throws WeixinException {
		String cacheKey = tokenCreator.getCacheKey();
		Token token = tokenStorager.lookupToken(cacheKey);
		if (token == null) {
			token = tokenCreator.createToken();
			tokenStorager.cachingToken(token, cacheKey);
		}
		return token;
	}
}
