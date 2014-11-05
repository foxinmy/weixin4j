package com.foxinmy.weixin4j.token;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * 基于redis保存的Token获取类
 * 
 * @className RedisTokenHolder
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token">获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class RedisTokenHolder extends AbstractTokenHolder {

	private JedisPool jedisPool;

	public RedisTokenHolder() {
		super();
	}

	public RedisTokenHolder(String appid, String appsecret) {
		this(appid, appsecret, "localhost", 6379);
	}

	public RedisTokenHolder(String appid, String appsecret, String host,
			int port) {
		super(appid, appsecret);
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(50);
		poolConfig.setMaxIdle(5);
		poolConfig.setMaxWaitMillis(2000);
		poolConfig.setTestOnBorrow(false);
		poolConfig.setTestOnReturn(true);
		this.jedisPool = new JedisPool(poolConfig, host, port);
	}

	@Override
	public Token getToken() throws WeixinException {
		String appid = getAppid();
		String appsecret = getAppsecret();
		if (StringUtils.isBlank(appid) || StringUtils.isBlank(appsecret)) {
			throw new IllegalArgumentException(
					"appid or appsecret not be null!");
		}
		Token token = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String key = String.format("token:%s", appid);
			String accessToken = jedis.get(key);
			if (StringUtils.isBlank(accessToken)) {
				String api_token_uri = String
						.format(tokenUrl, appid, appsecret);
				token = request.get(api_token_uri).getAsObject(
						new TypeReference<Token>() {
						});
				jedis.setex(key, token.getExpiresIn() - 3,
						token.getAccessToken());
			} else {
				token = new Token();
				token.setAccessToken(accessToken);
				token.setExpiresIn(jedis.ttl(key).intValue());
			}
			token.setTime(System.currentTimeMillis());
			token.setOpenid(appid);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return token;
	}
}
