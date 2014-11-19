package com.foxinmy.weixin4j.token;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.type.AccountType;

/**
 * 基于redis保存的Token获取类
 * 
 * @className RedisTokenHolder
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token">微信公众平台获取token说明</a>
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%BB%E5%8A%A8%E8%B0%83%E7%94%A8"
 *      >微信企业号获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class RedisTokenHolder extends AbstractTokenHolder {

	private JedisPool jedisPool;

	private void createPool(String host, int port) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(50);
		poolConfig.setMaxIdle(5);
		poolConfig.setMaxWaitMillis(2000);
		poolConfig.setTestOnBorrow(false);
		poolConfig.setTestOnReturn(true);
		this.jedisPool = new JedisPool(poolConfig, host, port);
	}

	public RedisTokenHolder(String host, int port, AccountType accountType) {
		super(accountType);
		createPool(host, port);
	}

	public RedisTokenHolder(AccountType accountType) {
		this("localhost", 6379, accountType);
	}

	public RedisTokenHolder(WeixinAccount weixinAccount) {
		this("localhost", 6379, weixinAccount);
	}

	public RedisTokenHolder(String host, int port, WeixinAccount weixinAccount) {
		super(weixinAccount);
		createPool(host, port);
	}

	@Override
	public Token getToken() throws WeixinException {
		String id = weixinAccount.getId();
		if (StringUtils.isBlank(id)
				|| StringUtils.isBlank(weixinAccount.getSecret())) {
			throw new IllegalArgumentException("id or secret not be null!");
		}
		Token token = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String key = String.format("token:%s", id);
			String accessToken = jedis.get(key);
			if (StringUtils.isBlank(accessToken)) {
				token = request.get(weixinAccount.getTokenUrl()).getAsObject(
						new TypeReference<Token>() {
						});
				jedis.setex(key, token.getExpiresIn() - 3,
						token.getAccessToken());
				token.setTime(System.currentTimeMillis());
			} else {
				token = new Token();
				token.setAccessToken(accessToken);
				token.setExpiresIn(jedis.ttl(key).intValue());
			}
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return token;
	}
}