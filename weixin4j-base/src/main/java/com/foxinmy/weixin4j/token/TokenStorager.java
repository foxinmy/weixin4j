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
	/**
	 * 考虑到程序的临界值,实际有效时间应该减去下面这个数
	 */
	final long CUTMS = 1 * 60 * 1000l;
	/**
	 * 缓存key的前缀
	 */
	final String PREFIX = "weixin4j_";
}
