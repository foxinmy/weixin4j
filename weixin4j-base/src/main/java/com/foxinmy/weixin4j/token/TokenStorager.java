package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.model.Token;

/**
 * Token的存储
 *
 * @className TokenStorager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月27日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.model.Token
 * @see MemoryTokenStorager
 * @see FileTokenStorager
 * @see RedisTokenStorager
 * @see MemcacheTokenStorager
 */
public abstract class TokenStorager implements CacheStorager<Token> {
	/**
	 * 考虑到临界情况,实际token的有效时间减去该毫秒数
	 *
	 * @return 默认为60秒
	 */
	public long ms() {
		return 60 * 1000l;
	}
}
