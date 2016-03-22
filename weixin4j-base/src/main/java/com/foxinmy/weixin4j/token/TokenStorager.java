package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.model.Token;

/**
 * token的存储
 * 
 * @className TokenStorager
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.model.Token
 * @see MemoryTokenStorager
 * @see FileTokenStorager
 * @see RedisTokenStorager
 */
public interface TokenStorager extends CacheStorager<Token> {
}
