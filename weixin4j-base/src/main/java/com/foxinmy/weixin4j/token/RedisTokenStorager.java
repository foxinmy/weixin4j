package com.foxinmy.weixin4j.token;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * 用REDIS保存TOKEN(推荐使用)
 * 
 * @className RedisTokenStorager
 * @author jy
 * @date 2015年1月9日
 * @since JDK 1.6
 */
public class RedisTokenStorager implements TokenStorager {

	private JedisPool jedisPool;

	public final static int PORT = 6379;
	public final static int MAX_TOTAL = 50;
	public final static int MAX_IDLE = 5;
	public final static int MAX_WAIT_MILLIS = 2000;
	public final static boolean TEST_ON_BORROW = false;
	public final static boolean TEST_ON_RETURN = true;

	public RedisTokenStorager() {
		this("localhost", PORT);
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

	public RedisTokenStorager(String host, int port, JedisPoolConfig jedisPoolConfig) {
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
			Map<String, String> map = jedis.hgetAll(cacheKey);
			if (map != null && !map.isEmpty()) {
				return map2token(map);
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
			jedis.hmset(cacheKey, token2map(token));
			if (token.getExpiresIn() > 0) {
				jedis.expire(cacheKey, token.getExpiresIn() - (int) (CUTMS / 1000l));
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	private final static String ACCESSTOKEN_KEY = "accessToken";
	private final static String EXPIRESIN_KEY = "expiresIn";
	private final static String CREATETIME_KEY = "createTime";
	private final static String ORIGINAL_KEY = "originalResult";

	protected Map<String, String> token2map(Token token) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(ACCESSTOKEN_KEY, token.getAccessToken());
		map.put(EXPIRESIN_KEY, Integer.toString(token.getExpiresIn()));
		map.put(CREATETIME_KEY, Long.toString(token.getCreateTime()));
		map.put(ORIGINAL_KEY, token.getOriginalResult());
		return map;
	}

	protected Token map2token(Map<String, String> map) {
		Token token = new Token(map.get(ACCESSTOKEN_KEY));
		token.setExpiresIn(Integer.parseInt(map.get(EXPIRESIN_KEY)));
		token.setCreateTime(Long.parseLong(map.get(CREATETIME_KEY)));
		token.setOriginalResult(map.get(ORIGINAL_KEY));
		return token;
	}

	@Override
	public Token evict(String cacheKey) throws WeixinException {
		Token token = lookup(cacheKey);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(cacheKey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return token;
	}

	@Override
	public void clear() throws WeixinException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<String> cacheKeys = jedis.keys(String.format("%s*", PREFIX));
			if (!cacheKeys.isEmpty()) {
				Pipeline pipeline = jedis.pipelined();
				for (String cacheKey : cacheKeys) {
					pipeline.del(cacheKey);
				}
				pipeline.sync();
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}