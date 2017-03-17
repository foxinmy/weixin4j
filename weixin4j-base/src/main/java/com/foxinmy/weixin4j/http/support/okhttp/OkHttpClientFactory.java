package com.foxinmy.weixin4j.http.support.okhttp;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;

/**
 * 使用OkHttp
 * 
 * @className OkHttpClientFactory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月25日
 * @since JDK 1.6
 */
public class OkHttpClientFactory extends HttpClientFactory {
	private static HttpClientFactory okHttpClientFactory;
	static {
		try {
			okHttpClientFactory = new OkHttpClient3Factory();
		} catch (Throwable e1) {
			try {
				okHttpClientFactory = new OkHttpClient2Factory();
			} catch (Throwable e2) {
				throw new RuntimeException(e2);
			}
		}
	}

	@Override
	public HttpClient newInstance(HttpParams params) {
		return okHttpClientFactory.newInstance(params);
	}
}
