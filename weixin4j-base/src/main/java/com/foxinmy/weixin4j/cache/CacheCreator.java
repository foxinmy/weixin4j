package com.foxinmy.weixin4j.cache;

import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * Cache的创建
 *
 * @className CacheCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年5月24日
 * @since JDK 1.6
 * @see
 */
public interface CacheCreator<T extends Cacheable> {
	/**
	 * CacheKey
	 *
	 * @return
	 */
	public String key();

	/**
	 * 创建Cache
	 *
	 * @throws WeixinException
	 * @return 缓存对象
	 */
	public T create() throws WeixinException;
}
