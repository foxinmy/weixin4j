package com.foxinmy.weixin4j.cache;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * 缓存管理类
 *
 * @className CacheManager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年5月27日
 * @since JDK 1.7
 * @see
 */
public class CacheManager<T extends Cacheable> {
    protected final CacheCreator<T> cacheCreator;
    protected final CacheStorager<T> cacheStorager;
    private final ReentrantLock lock = new ReentrantLock();

    public CacheManager(CacheCreator<T> cacheCreator, CacheStorager<T> cacheStorager) {
        this.cacheCreator = cacheCreator;
        this.cacheStorager = cacheStorager;
    }

    /**
     * 获取缓存对象
     *
     * @return 缓存对象
     * @throws WeixinException
     */
    public T getCache() throws WeixinException {
        String cacheKey = cacheCreator.key();
        T cache = cacheStorager.lookup(cacheKey);
        try {
            if (cache == null && lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    cache = cacheStorager.lookup(cacheKey);
                    if (cache == null) {
                        cache = cacheCreator.create();
                        cacheStorager.caching(cacheKey, cache);
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new WeixinException("get cache error on lock", e);
        }
        return cache;
    }

    /**
     * 刷新缓存对象
     *
     * @return 缓存对象
     * @throws WeixinException
     */
    public T refreshCache() throws WeixinException {
        String cacheKey = cacheCreator.key();
        T cache = cacheCreator.create();
        cacheStorager.caching(cacheKey, cache);
        return cache;
    }

    /**
     * 移除缓存
     *
     * @return 被移除的缓存对象
     */
    public T evictCache() {
        String cacheKey = cacheCreator.key();
        return cacheStorager.evict(cacheKey);
    }

    /**
     * 清除所有的缓存(<font color="red">请慎重</font>)
     */
    public void clearCache() {
        cacheStorager.clear();
    }
}
