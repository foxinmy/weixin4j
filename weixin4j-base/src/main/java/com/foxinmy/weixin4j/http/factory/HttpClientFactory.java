package com.foxinmy.weixin4j.http.factory;

import com.foxinmy.weixin4j.http.HttpClient;

/**
 * HttpClient工厂生产类:参考netty的InternalLoggerFactory
 * 
 * @className HttpClientFactory
 * @author jy
 * @date 2015年8月12日
 * @since JDK 1.7
 * @see
 */
public abstract class HttpClientFactory {

	/**
	 * 默认的HttpClient
	 */
	private static volatile HttpClientFactory defaultFactory = newDefaultFactory();

	/**
	 * ApachHttpClient -> SimpleHttpClient(HttpURLConnection)
	 * 
	 * @return
	 */
	private static HttpClientFactory newDefaultFactory() {
		HttpClientFactory f;
		try {
			f = new HttpComponent4Factory();
		} catch (Throwable e1) {
			try {
				f = new HttpComponent3Factory();
			} catch (Throwable e2) {
				f = new SimpleHttpClientFactory();
			}
		}
		return f;
	}

	/**
	 * 获取默认的HttpClient
	 * 
	 * @return
	 */
	public static HttpClientFactory getDefaultFactory() {
		return defaultFactory;
	}

	/**
	 * 显式设置默认的HttpClient
	 * 
	 * @param defaultFactory
	 */
	public static void setDefaultFactory(HttpClientFactory defaultFactory) {
		if (defaultFactory == null) {
			throw new NullPointerException("defaultFactory");
		}
		HttpClientFactory.defaultFactory = defaultFactory;
	}

	/**
	 * 获取HttpClient实例
	 * 
	 * @return
	 */
	public static HttpClient getInstance() {
		return getDefaultFactory().newInstance();
	}

	/**
	 * 获取HttpClient实例
	 * 
	 * @return
	 */
	public abstract HttpClient newInstance();
}
