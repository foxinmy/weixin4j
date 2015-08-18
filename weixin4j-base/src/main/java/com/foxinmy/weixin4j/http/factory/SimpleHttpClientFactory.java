package com.foxinmy.weixin4j.http.factory;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.SimpleHttpClient;

/**
 * HttpURLConnection的简单实现
 * 
 * @className SimpleHttpClientFactory
 * @author jy
 * @date 2015年8月12日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.http.SimpleHttpClient
 */
public class SimpleHttpClientFactory extends HttpClientFactory {

	@Override
	public HttpClient newInstance() {
		return new SimpleHttpClient();
	}
}
