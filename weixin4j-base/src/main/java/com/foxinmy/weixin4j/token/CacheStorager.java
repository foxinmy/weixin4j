package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * cache存储
 * 
 * @className CacheStorager
 * @author jy
 * @date 2015年6月22日
 * @since JDK 1.6
 * @see
 */
public interface CacheStorager<T> {
	/**
	 * 查找缓存中的对象
	 * 
	 * @param cacheKey
	 *            缓存key
	 * @return 缓存对象
	 * @throws WeixinException
	 */
	T lookup(String cacheKey) throws WeixinException;

	/**
	 * 缓存新的对象
	 * 
	 * @param cacheKey
	 *            缓存key
	 * 
	 * @param t
	 *            将要缓存的对象
	 * @throws WeixinException
	 */
	void caching(String cacheKey, T t) throws WeixinException;

	/**
	 * 移除缓存对象
	 * 
	 * @param cacheKey
	 *            缓存key
	 * @return 移除的对象
	 */
	T evict(String cacheKey) throws WeixinException;

	/**
	 * 清除所有缓存对象(<font color="red">请慎重</font>)
	 */
	void clear() throws WeixinException;
}
