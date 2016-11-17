package com.foxinmy.weixin4j.base.test.http;

import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.support.okhttp.OkHttpClient3Factory;

/**
 * OkHttp for test
 * 
 * @className OkHttpClinetTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月28日
 * @since JDK 1.6
 */
public class OkHttp3ClinetTest extends HttpClientTest {
	@Override
	protected HttpClientFactory createHttpFactory() {
		return new OkHttpClient3Factory();
	}
}
