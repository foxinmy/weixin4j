package com.foxinmy.weixin4j.test;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;

/**
 * token相关测试
 * @className TokenTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class TokenTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		weixinProxy = new WeixinProxy();
	}

	@Test
	public void getToken() {
		try {
			System.out.println(weixinProxy.getToken());
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
}
