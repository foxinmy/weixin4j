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
 * @see MemcacheTokenStorager
 */
public interface TokenStorager extends CacheStorager<Token> {
	/**
	 * 考虑到临界情况,在实际有效时间上减去60秒
	 */
	final long CUTMS = 60 * 1000l;
	/**
	 * 缓存key的前缀
	 */
	final String PREFIX = "weixin4j_";
}
