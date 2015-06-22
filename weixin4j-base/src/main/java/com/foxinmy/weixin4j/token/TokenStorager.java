package com.foxinmy.weixin4j.token;

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
public interface TokenStorager extends CacheStorager<Token> {
}
