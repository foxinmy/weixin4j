package com.foxinmy.weixin4j.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.api.token.FileTokenApi;
import com.foxinmy.weixin4j.api.token.TokenApi;
import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * token测试
 * 
 * @className TokenTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class TokenTest {

	protected TokenApi tokenApi;

	@Before
	public void setUp() {
		tokenApi = new FileTokenApi();
	}

	@Test
	public void test() throws WeixinException {
		Assert.assertNotNull(tokenApi.getToken());
	}
}
