package com.foxinmy.weixin4j.token;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 用REDIS保存TOKEN
 * 
 * @className RedisTokenHolder
 * @author jy
 * @date 2015年1月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.token.TokenCreator
 */
public class RedisTokenHolder implements TokenHolder {

	private JedisPool jedisPool;
	private final TokenCreator tokenCreator;

	public final static int MAX_TOTAL = 50;
	public final static int MAX_IDLE = 5;
	public final static int MAX_WAIT_MILLIS = 2000;
	public final static boolean TEST_ON_BORROW = false;
	public final static boolean TEST_ON_RETURN = true;

	public RedisTokenHolder(TokenCreator tokenCreator) {
		this("localhost", 6379, tokenCreator);
	}

	public RedisTokenHolder(String host, int port, TokenCreator tokenCreator) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(MAX_TOTAL);
		jedisPoolConfig.setMaxIdle(MAX_IDLE);
		jedisPoolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
		jedisPoolConfig.setTestOnBorrow(TEST_ON_BORROW);
		jedisPoolConfig.setTestOnReturn(TEST_ON_RETURN);
		this.jedisPool = new JedisPool(jedisPoolConfig, host, port);
		this.tokenCreator = tokenCreator;
	}

	public RedisTokenHolder(String host, int port,
			JedisPoolConfig jedisPoolConfig, TokenCreator tokenCreator) {
		this(new JedisPool(jedisPoolConfig, host, port), tokenCreator);
	}

	public RedisTokenHolder(JedisPool jedisPool, TokenCreator tokenCreator) {
		this.jedisPool = jedisPool;
		this.tokenCreator = tokenCreator;
	}

	@Override
	public Token getToken() throws WeixinException {
		Token token = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String cacheKey = tokenCreator.getCacheKey();
			String accessToken = jedis.get(cacheKey);
			if (StringUtil.isBlank(accessToken)) {
				token = tokenCreator.createToken();
				jedis.setex(cacheKey, (int) token.getExpiresIn(),
						token.getAccessToken());
			} else {
				token = new Token(accessToken);
			}
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return token;
	}
}
