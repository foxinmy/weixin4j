package com.foxinmy.weixin4j.token;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 用REDIS保存TOKEN
 * 
 * @className RedisTokenStorager
 * @author jy
 * @date 2015年1月9日
 * @since JDK 1.6
 */
public class RedisTokenStorager implements TokenStorager {

	private JedisPool jedisPool;

	public final static int MAX_TOTAL = 50;
	public final static int MAX_IDLE = 5;
	public final static int MAX_WAIT_MILLIS = 2000;
	public final static boolean TEST_ON_BORROW = false;
	public final static boolean TEST_ON_RETURN = true;

	public RedisTokenStorager() {
		this("localhost", 6379);
	}

	public RedisTokenStorager(String host, int port) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(MAX_TOTAL);
		jedisPoolConfig.setMaxIdle(MAX_IDLE);
		jedisPoolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
		jedisPoolConfig.setTestOnBorrow(TEST_ON_BORROW);
		jedisPoolConfig.setTestOnReturn(TEST_ON_RETURN);
		this.jedisPool = new JedisPool(jedisPoolConfig, host, port);
	}

	public RedisTokenStorager(String host, int port,
			JedisPoolConfig jedisPoolConfig) {
		this(new JedisPool(jedisPoolConfig, host, port));
	}

	public RedisTokenStorager(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public Token lookup(String cacheKey) throws WeixinException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String accessToken = jedis.get(cacheKey);
			if (!StringUtil.isBlank(accessToken)) {
				return new Token(accessToken);
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return null;
	}

	@Override
	public void caching(String cacheKey, Token token) throws WeixinException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if (token.getExpiresIn() > 0) {
				jedis.setex(cacheKey, (int) token.getExpiresIn(),
						token.getAccessToken());
			} else {
				jedis.set(cacheKey, token.getAccessToken());
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}