package com.foxinmy.weixin4j.base.test.http;

import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.support.apache3.HttpComponent3Factory;

/**
 * Apache3 for http test
 * 
 * @className Apache3HttpClientTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月28日
 * @since JDK 1.6
 */
public class Apache3HttpClientTest extends HttpClientTest {

	@Override
	protected HttpClientFactory createHttpFactory() {
		return new HttpComponent3Factory();
	}
}
