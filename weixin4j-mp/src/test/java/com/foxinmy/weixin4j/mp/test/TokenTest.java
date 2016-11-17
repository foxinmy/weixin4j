package com.foxinmy.weixin4j.mp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * token测试
 *
 * @className TokenTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class TokenTest {

	protected TokenManager tokenManager;
	protected WeixinAccount weixinAccount;

	@Before
	public void setUp() {
		this.weixinAccount = Weixin4jConfigUtil.getWeixinAccount();
		tokenManager = new TokenManager(new WeixinTokenCreator(
				weixinAccount.getId(), weixinAccount.getSecret()),
				new FileCacheStorager<Token>());
	}

	@Test
	public void test() throws WeixinException {
		Assert.assertNotNull(tokenManager.getCache());
	}
}
