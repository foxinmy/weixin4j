package com.foxinmy.weixin4j.token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * 用内存保存TOKEN(不推荐使用)
 * 
 * @className MemoryTokenStorager
 * @author jy
 * @date 2016年1月24日
 * @since JDK 1.6
 * @see
 */
public class MemoryTokenStorager implements TokenStorager {

	private final Map<String, Token> CONMAP;

	public MemoryTokenStorager() {
		this.CONMAP = new ConcurrentHashMap<String, Token>();
	}

	@Override
	public Token lookup(String cacheKey) throws WeixinException {
		Token token = this.CONMAP.get(cacheKey);
		if (token != null) {
			if ((token.getCreateTime() + (token.getExpiresIn() * 1000l) - 2) > System
					.currentTimeMillis()) {
				return token;
			}
		}
		return null;
	}

	@Override
	public void caching(String cacheKey, Token token) throws WeixinException {
		this.CONMAP.put(cacheKey, token);
	}

	@Override
	public Token evict(String cacheKey) throws WeixinException {
		return this.CONMAP.remove(cacheKey);
	}

	@Override
	public void clear() throws WeixinException {
		this.CONMAP.clear();
	}
}
