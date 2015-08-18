package com.foxinmy.weixin4j.http.factory;

import org.apache.commons.httpclient.HttpClient;

/**
 * 使用Apache的HttpClient3.x
 * 
 * @className HttpComponent3Factory
 * @author jy
 * @date 2015年8月12日
 * @since JDK 1.7
 * @see
 */
public class HttpComponent3Factory extends HttpClientFactory {

	@Override
	public com.foxinmy.weixin4j.http.HttpClient newInstance() {
		return new HttpComponent3(new HttpClient());
	}
}
