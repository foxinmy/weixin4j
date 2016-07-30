package com.foxinmy.weixin4j.base.test.http;

import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.support.apache4.HttpComponent4_1Factory;

/**
 * Apache4 for http test
 * @className Apache4HttpClientTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月28日
 * @since JDK 1.6
 */
public class Apache4_1HttpClientTest extends HttpClientTest {

	@Override
	protected HttpClientFactory createHttpFactory() {
		return new HttpComponent4_1Factory();
	}
}
