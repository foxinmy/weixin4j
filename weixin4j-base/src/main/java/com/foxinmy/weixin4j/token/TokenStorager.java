package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * token的存储
 * 
 * @className TokenStorager
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.model.Token
 * @see com.foxinmy.weixin4j.token.FileTokenStorager
 * @see com.foxinmy.weixin4j.token.RedisTokenStorager
 */
public interface TokenStorager {
	/**
	 * 查找缓存的token
	 * 
	 * @param cacheKey
	 *            缓存的名称
	 * @return 查找结果
	 * @throws WeixinException
	 */
	public Token lookupToken(String cacheKey) throws WeixinException;

	/**
	 * 缓存新的token
	 * 
	 * @param token
	 *            新产生的token
	 * @param cacheKey
	 *            缓存的名称
	 * @throws WeixinException
	 */
	public void cachingToken(Token token, String cacheKey)
			throws WeixinException;
}
