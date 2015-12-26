package com.foxinmy.weixin4j.http.factory;

import org.apache.commons.httpclient.HttpClient;

/**
 * 使用Apache的HttpClient3.x
 * 
 * @className HttpComponent3Factory
 * @author jy
 * @date 2015年8月12日
 * @since JDK 1.6
 * @see
 */
public class HttpComponent3Factory extends HttpClientFactory {

	public HttpComponent3Factory() {
		// odd code
		Class<HttpClient> _dead = HttpClient.class;
	}

	@Override
	public com.foxinmy.weixin4j.http.HttpClient newInstance() {
		return new HttpComponent3(new HttpClient());
	}
}
