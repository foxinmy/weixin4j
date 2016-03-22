package com.foxinmy.weixin4j.http.factory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;

/**
 * HttpClient工厂生产类:参考netty的InternalLoggerFactory
 * 
 * @className HttpClientFactory
 * @author jy
 * @date 2015年8月12日
 * @since JDK 1.6
 * @see
 */
public abstract class HttpClientFactory {

	/**
	 * 默认的HttpClient
	 */
	private static volatile HttpClientFactory defaultFactory = newDefaultFactory();

	/**
	 * NettyHttpClient -> ApacheHttpClient ->
	 * SimpleHttpClient(HttpURLConnection)
	 * 
	 * @return
	 */
	private static HttpClientFactory newDefaultFactory() {
		HttpClientFactory f;
		try {
			f = new Netty4HttpClientFactory();
		} catch (Throwable e1) {
			try {
				f = new HttpComponent4Factory();
			} catch (Throwable e2) {
				try {
					f = new HttpComponent3Factory();
				} catch (Throwable e3) {
					f = new SimpleHttpClientFactory();
				}
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

	public static SSLContext allowSSLContext() throws HttpClientException {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null,
					new X509TrustManager[] { createX509TrustManager() },
					new java.security.SecureRandom());
			return sslContext;
		} catch (NoSuchAlgorithmException e) {
			throw new HttpClientException(
					"Create SSLContext NoSuchAlgorithmException:", e);
		} catch (KeyManagementException e) {
			throw new HttpClientException(
					"Create SSLContext KeyManagementException:", e);
		}
	}

	protected static X509TrustManager createX509TrustManager() {
		return new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkServerTrusted(
					X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkClientTrusted(
					X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}
		};
	}
}
