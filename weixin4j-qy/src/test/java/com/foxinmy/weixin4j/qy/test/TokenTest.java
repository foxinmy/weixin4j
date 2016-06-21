package com.foxinmy.weixin4j.qy.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.qy.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.setting.Weixin4jSettings;
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
	protected Weixin4jSettings<WeixinAccount> settings;

	@Before
	public void setUp() {
		this.settings = new Weixin4jSettings<WeixinAccount>(
				Weixin4jConfigUtil.getWeixinAccount());
		tokenManager = new TokenManager(new WeixinTokenCreator(settings
				.getAccount().getId(), settings.getAccount().getSecret()),
				settings.getCacheStorager0());
	}

	@Test
	public void test() throws WeixinException {
		Assert.assertNotNull(tokenManager.getCache());
	}
}
