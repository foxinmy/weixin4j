package com.foxinmy.weixin4j.cache;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.util.SerializationUtils;

/**
 * 用Redis保存缓存对象(推荐使用)
 *
 * @className RedisCacheStorager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月9日
 * @since JDK 1.6
 */
public class RedisCacheStorager<T extends Cacheable> implements
		CacheStorager<T> {

	private JedisPool jedisPool;

	private final static String HOST = "127.0.0.1";
	private final static int PORT = 6379;
	private final static int TIMEOUT = 5000;
	private final static int MAX_TOTAL = 50;
	private final static int MAX_IDLE = 5;
	private final static int MAX_WAIT_MILLIS = 5000;
	private final static boolean TEST_ON_BORROW = false;
	private final static boolean TEST_ON_RETURN = true;

	public RedisCacheStorager() {
		this(HOST, PORT, TIMEOUT);
	}

	public RedisCacheStorager(String host, int port, int timeout) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(MAX_TOTAL);
		jedisPoolConfig.setMaxIdle(MAX_IDLE);
		jedisPoolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
		jedisPoolConfig.setTestOnBorrow(TEST_ON_BORROW);
		jedisPoolConfig.setTestOnReturn(TEST_ON_RETURN);
		this.jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
	}

	public RedisCacheStorager(JedisPoolConfig jedisPoolConfig) {
		this(new JedisPool(jedisPoolConfig, HOST, PORT, TIMEOUT));
	}

	public RedisCacheStorager(String host, int port, int timeout,
			JedisPoolConfig jedisPoolConfig) {
		this(new JedisPool(jedisPoolConfig, host, port, timeout));
	}

	public RedisCacheStorager(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T lookup(String cacheKey) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] value = jedis.get(cacheKey.getBytes(Consts.UTF_8));
			return value != null ? (T) SerializationUtils.deserialize(value)
					: null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public void caching(String cacheKey, T cache) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] key = cacheKey.getBytes(Consts.UTF_8);
			byte[] value = SerializationUtils.serialize(cache);
			if (cache.getExpires() > 0) {
				jedis.setex(key, (int) (cache.getExpires() - CUTMS) / 1000,
						value);
			} else {
				jedis.set(key, value);
			}
			jedis.sadd(ALLKEY, cacheKey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public T evict(String cacheKey) {
		T cache = lookup(cacheKey);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(cacheKey);
			jedis.srem(ALLKEY, cacheKey);
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