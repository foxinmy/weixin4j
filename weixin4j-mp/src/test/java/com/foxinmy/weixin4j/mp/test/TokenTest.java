package com.foxinmy.weixin4j.mp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * token测试
 * 
 * @className TokenTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class TokenTest {

	protected TokenHolder tokenHolder;

	@Before
	public void setUp() {
		WeixinAccount weixinAccount = Weixin4jConfigUtil.getWeixinAccount();
		tokenHolder = new TokenHolder(new WeixinTokenCreator(
				weixinAccount.getId(), weixinAccount.getSecret()),
				new FileTokenStorager(Weixin4jConfigUtil.getValue("token_path",
						"/tmp/weixin4j/token")));
	}

	@Test
	public void test() throws WeixinException {
		Assert.assertNotNull(tokenHolder.getToken());
	}
}
