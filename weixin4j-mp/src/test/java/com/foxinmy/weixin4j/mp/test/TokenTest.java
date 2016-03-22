package com.foxinmy.weixin4j.mp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.Weixin4jSettings;

/**
 * token测试
 * 
 * @className TokenTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class TokenTest {

	protected TokenHolder tokenHolder;
	protected Weixin4jSettings settings;

	@Before
	public void setUp() {
		this.settings = new Weixin4jSettings();
		tokenHolder = new TokenHolder(new WeixinTokenCreator(settings
				.getWeixinAccount().getId(), settings.getWeixinAccount()
				.getSecret()), settings.getTokenStorager0());
	}

	@Test
	public void test() throws WeixinException {
		Assert.assertNotNull(tokenHolder.getToken());
	}
}
