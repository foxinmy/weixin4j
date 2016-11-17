package com.foxinmy.weixin4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用内存保存缓存对象(不推荐使用)
 *
 * @className MemoryCacheStorager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年1月24日
 * @since JDK 1.6
 * @see
 */
public class MemoryCacheStorager<T extends Cacheable> implements
		CacheStorager<T> {

	private final Map<String, T> CONMAP;

	public MemoryCacheStorager() {
		this.CONMAP = new ConcurrentHashMap<String, T>();
	}

	@Override
	public T lookup(String key) {
		T cache = this.CONMAP.get(key);
		if (cache != null) {
			if ((cache.getCreateTime() + cache.getExpires() - CUTMS) > System
					.currentTimeMillis()) {
				return cache;
			}
		}
		return null;
	}

	@Override
	public void caching(String key, T cache) {
		this.CONMAP.put(key, cache);
	}

	@Override
	public T evict(String key) {
		return this.CONMAP.remove(key);
	}

	@Override
	public void clear() {
		this.CONMAP.clear();
	}
}
