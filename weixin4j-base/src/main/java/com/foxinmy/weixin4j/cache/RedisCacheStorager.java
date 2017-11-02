package com.foxinmy.weixin4j.cache;

import java.util.Set;

import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

/**
 * 用Redis保存缓存对象(推荐使用)
 *
 * @className RedisCacheStorager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月9日
 * @since JDK 1.6
 */
public class RedisCacheStorager<T extends Cacheable> implements CacheStorager<T> {

    private Pool<Jedis> jedisPool;

    private final static String HOST = "127.0.0.1";
    private final static int PORT = 6379;
    private final static int TIMEOUT = 5000;
    private final static int MAX_TOTAL = 50;
    private final static int MAX_IDLE = 5;
    private final static int MAX_WAIT_MILLIS = 5000;
    private final static boolean TEST_ON_BORROW = false;
    private final static boolean TEST_ON_RETURN = true;
    private final static JedisPoolConfig POOLCONFIG;
    static {
        POOLCONFIG = new JedisPoolConfig();
        POOLCONFIG.setMaxTotal(MAX_TOTAL);
        POOLCONFIG.setMaxIdle(MAX_IDLE);
        POOLCONFIG.setMaxWaitMillis(MAX_WAIT_MILLIS);
        POOLCONFIG.setTestOnBorrow(TEST_ON_BORROW);
        POOLCONFIG.setTestOnReturn(TEST_ON_RETURN);
    }

    public RedisCacheStorager() {
        this(HOST, PORT, TIMEOUT);
    }

    public RedisCacheStorager(String host, int port, int timeout) {
        this(host, port, timeout, null, POOLCONFIG);
    }

    public RedisCacheStorager(String host, int port, int timeout, String password) {
        this(host, port, timeout, password, POOLCONFIG);
    }

    public RedisCacheStorager(JedisPoolConfig poolConfig) {
        this(new JedisPool(poolConfig, HOST, PORT, TIMEOUT));
    }

    public RedisCacheStorager(String host, int port, int timeout, String password, JedisPoolConfig poolConfig) {
        this(new JedisPool(poolConfig, host, port, timeout, password));
    }

    public RedisCacheStorager(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T lookup(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] value = jedis.get(key.getBytes(Consts.UTF_8));
            return value != null ? (T) SerializationUtils.deserialize(value) : null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void caching(String key, T cache) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] cacheKey = key.getBytes(Consts.UTF_8);
            byte[] value = SerializationUtils.serialize(cache);
            if (cache.getExpires() > 0) {
                jedis.setex(cacheKey, (int) (cache.getExpires() - CUTMS) / 1000, value);
            } else {
                jedis.set(cacheKey, value);
            }
            jedis.sadd(ALLKEY, key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public T evict(String key) {
        T cache = lookup(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            jedis.srem(ALLKEY, key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return cache;
    }

    @Override
    public void clear() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> cacheKeys = jedis.smembers(ALLKEY);
            if (!cacheKeys.isEmpty()) {
                cacheKeys.add(ALLKEY);
                jedis.del(cacheKeys.toArray(new String[cacheKeys.size()]));
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
