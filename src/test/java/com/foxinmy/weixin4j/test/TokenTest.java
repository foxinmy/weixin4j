package com.foxinmy.weixin4j.test;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.api.token.FileTokenApi;
import com.foxinmy.weixin4j.api.token.TokenApi;
import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * token相关测试
 * @className TokenTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class TokenTest {
	private TokenApi tokenApi;

	@Before
	public void init() {
		tokenApi = new FileTokenApi();
	}

	@Test
	public void getToken() {
		try {
			System.out.println(tokenApi.getToken());
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
}
