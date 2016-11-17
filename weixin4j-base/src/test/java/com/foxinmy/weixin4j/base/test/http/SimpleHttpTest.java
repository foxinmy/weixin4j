package com.foxinmy.weixin4j.base.test.http;

import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.factory.SimpleHttpClientFactory;

/**
 * SimpleClient for test
 * @className SimpleHttpTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月28日
 * @since JDK 1.6
 */
public class SimpleHttpTest extends HttpClientTest {

	@Override
	protected HttpClientFactory createHttpFactory() {
		return new SimpleHttpClientFactory();
	}
}
