package com.foxinmy.weixin4j.mp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * token测试
 * 
 * @className TokenTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class TokenTest {

	protected TokenHolder tokenApi;

	@Before
	public void setUp() {
		tokenApi = new FileTokenHolder();
	}

	@Test
	public void test() throws WeixinException {
		Assert.assertNotNull(tokenApi.getToken());
	}
}
