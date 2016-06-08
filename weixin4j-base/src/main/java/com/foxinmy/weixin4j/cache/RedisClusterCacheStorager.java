package com.foxinmy.weixin4j.cache;

import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.util.SerializationUtils;

/**
 * 用Redis(集群)保存缓存对象(推荐使用)
 *
 * @className RedisClusterCacheStorager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年5月28日
 * @since JDK 1.6
 */
public class RedisClusterCacheStorager<T extends Cacheable> implements
		CacheStorager<T> {
	private final static int CONNECTION_TIMEOUT = 5000;
	private final static int SO_TIMEOUT = 5000;
	private final static int MAX_REDIRECTIONS = 5;
	private final static int MAX_TOTAL = 50;
	private final static int MAX_IDLE = 5;
	private final static int MAX_WAIT_MILLIS = 5000;
	private final static boolean TEST_ON_BORROW = false;
	private final static boolean TEST_ON_RETURN = true;
	private final JedisCluster jedisCluster;

	public RedisClusterCacheStorager(Set<HostAndPort> nodes) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(MAX_TOTAL);
		jedisPoolConfig.setMaxIdle(MAX_IDLE);
		jedisPoolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
		jedisPoolConfig.setTestOnBorrow(TEST_ON_BORROW);
		jedisPoolConfig.setTestOnReturn(TEST_ON_RETURN);
		this.jedisCluster = new JedisCluster(nodes, CONNECTION_TIMEOUT,
				SO_TIMEOUT, MAX_REDIRECTIONS, jedisPoolConfig);
	}

	public RedisClusterCacheStorager(Set<HostAndPort> nodes,
			JedisPoolConfig poolConfig) {
		this(nodes, CONNECTION_TIMEOUT, SO_TIMEOUT, MAX_REDIRECTIONS,
				poolConfig);
	}

	public RedisClusterCacheStorager(Set<HostAndPort> nodes,
			int connectionTimeout, int soTimeout, int maxRedirections,
			JedisPoolConfig poolConfig) {
		this(new JedisCluster(nodes, connectionTimeout, soTimeout,
				maxRedirections, poolConfig));
	}

	public RedisClusterCacheStorager(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T lookup(String cacheKey) {
		byte[] value = jedisCluster.get(cacheKey.getBytes(Consts.UTF_8));
		return value != null ? (T) SerializationUtils.deserialize(value) : null;
	}

	@Override
	public void caching(String cacheKey, T cache) {
		byte[] key = cacheKey.getBytes(Consts.UTF_8);
		byte[] value = SerializationUtils.serialize(cache);
		if (cache.getExpires() > 0) {
			jedisCluster.setex(key, (int) (cache.getExpires() - CUTMS) / 1000,
					value);
		} else {
			jedisCluster.set(key, value);
		}
		jedisCluster.sadd(ALLKEY, cacheKey);
	}

	@Override
	public T evict(String cacheKey) {
		T cache = lookup(cacheKey);
		jedisCluster.del(cacheKey);
		jedisCluster.srem(ALLKEY, cacheKey);
		return cache;
	}

	@Override
	public void clear() {
		Set<String> cacheKeys = jedisCluster.smembers(ALLKEY);
		if (!cacheKeys.isEmpty()) {
			cacheKeys.add(ALLKEY);
			jedisCluster.del(cacheKeys.toArray(new String[cacheKeys.size()]));
		}
	}
}