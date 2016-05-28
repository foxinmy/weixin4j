package com.foxinmy.weixin4j.cache;

/**
 * Cache的存储
 *
 * @className CacheStorager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月22日
 * @since JDK 1.6
 * @see
 */
public interface CacheStorager<T extends Cacheable> {
	/**
	 * 考虑到临界情况,实际缓存的有效时间减去该毫秒数(60秒)
	 */
	long CUTMS = 60 * 1000l;

	/**
	 * 所有的缓存KEY
	 */
	String ALLKEY = "weixin4j_cache_keys";

	/**
	 * 查找缓存中的对象
	 *
	 * @param key
	 *            缓存key
	 * @return 缓存对象
	 */
	T lookup(String key);

	/**
	 * 缓存新的对象
	 *
	 * @param key
	 *            缓存key
	 *
	 * @param cache
	 *            将要缓存的对象
	 */
	void caching(String key, T cache);

	/**
	 * 移除缓存对象
	 *
	 * @param key
	 *            缓存key
	 * @return 移除的对象
	 */
	T evict(String key);

	/**
	 * 清除所有缓存对象(<font color="red">请慎重</font>)
	 *
	 */
	void clear();
}
