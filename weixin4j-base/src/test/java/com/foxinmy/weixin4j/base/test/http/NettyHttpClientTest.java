package com.foxinmy.weixin4j.base.test.http;

import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.support.netty.Netty4HttpClientFactory;

/**
 * Netty for http test
 * @className NettyHttpClientTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月28日
 * @since JDK 1.6
 */
public class NettyHttpClientTest extends HttpClientTest {

	@Override
	protected HttpClientFactory createHttpFactory() {
		return new Netty4HttpClientFactory();
	}
}
