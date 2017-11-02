package com.foxinmy.weixin4j.http.factory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.support.apache3.HttpComponent3Factory;
import com.foxinmy.weixin4j.http.support.apache4.HttpComponent4Factory;
import com.foxinmy.weixin4j.http.support.netty.Netty4HttpClientFactory;
import com.foxinmy.weixin4j.http.support.okhttp.OkHttpClientFactory;

/**
 * HttpClient工厂生产类:参考netty的InternalLoggerFactory
 * 
 * @className HttpClientFactory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月12日
 * @since JDK 1.6
 * @see
 */
public abstract class HttpClientFactory {

	/**
	 * 默认的HttpClient
	 */
	private static volatile HttpClientFactory defaultFactory = newDefaultFactory();
	private static volatile HttpParams defaultParams;

	/**
	 * NettyHttpClient -> ApacheHttpClient(HttpComponent3&4) ->
	 * OkHttpClient(2&3) -> SimpleHttpClient(HttpURLConnection)
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
					try {
						f = new OkHttpClientFactory();
					} catch (Throwable e4) {
						f = new SimpleHttpClientFactory();
					}
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
			throw new IllegalArgumentException(
					"'defaultFactory' must not be empty");
		}
		HttpClientFactory.defaultFactory = defaultFactory;
	}

	/**
	 * 获取默认的HttpParams
	 * 
	 * @return
	 */
	public static HttpParams getDefaultParams() {
		return defaultParams;
	}

	/**
	 * Resolve the Http Parameter
	 * 
	 * @param params
	 *            请求参数
	 */
	public static void setDefaultParams(HttpParams params) {
		if (params == null) {
			throw new IllegalArgumentException("'params' must not be empty");
		}
		HttpClientFactory.defaultParams = params;
	}

	/**
	 * 获取HttpClient实例
	 * 
	 * @return
	 */
	public static HttpClient getInstance() {
		return getInstance(HttpClientFactory.defaultParams);
	}

	/**
	 * 获取HttpClient实例
	 * 
	 * @param params
	 *            Http参数
	 * 
	 * @return HttpClinet实例
	 */
	public static HttpClient getInstance(HttpParams params) {
		HttpClientFactory clientFactory = getDefaultFactory();
		return clientFactory.newInstance(params);
	}

	/**
	 * 获取HttpClient实例
	 * 
	 * @param params
	 *            http参数 可为空
	 * @return
	 */
	public abstract HttpClient newInstance(HttpParams params);

	public static SSLContext allowSSLContext() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null,
					new X509TrustManager[] { AllowX509TrustManager.GLOBAL },
					new java.security.SecureRandom());
			return sslContext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(
					"Create SSLContext NoSuchAlgorithmException:", e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(
					"Create SSLContext KeyManagementException:", e);
		}
	}

	public static class AllowX509TrustManager implements X509TrustManager {
		public static final X509TrustManager GLOBAL = new AllowX509TrustManager();

		private AllowX509TrustManager() {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
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
	}

	public static class AllowHostnameVerifier implements HostnameVerifier {
		public static final HostnameVerifier GLOBAL = new AllowHostnameVerifier();

		private AllowHostnameVerifier() {
		}

		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
