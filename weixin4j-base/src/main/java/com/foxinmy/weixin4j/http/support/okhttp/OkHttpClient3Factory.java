package com.foxinmy.weixin4j.http.support.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;

/**
 * 使用OkHttp3
 * 
 * @className OkHttpClient3Factory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月25日
 * @since JDK 1.6
 */
public class OkHttpClient3Factory extends HttpClientFactory {
	private volatile OkHttpClient okClient;
	private final OkHttpClient.Builder clientBuilder;

	public OkHttpClient3Factory() {
		clientBuilder = new OkHttpClient.Builder();
		clientBuilder
				.hostnameVerifier(HttpClientFactory.AllowHostnameVerifier.GLOBAL);
		clientBuilder.sslSocketFactory(HttpClientFactory.allowSSLContext()
				.getSocketFactory(),
				HttpClientFactory.AllowX509TrustManager.GLOBAL);
	}

	public OkHttpClient3Factory(OkHttpClient.Builder clientBuilder) {
		this.clientBuilder = clientBuilder;
	}

	/**
	 * resolve Request.Parameter
	 * 
	 * */
	private void resolveHttpParams(HttpParams params) {
		if (params != null) {
			clientBuilder.connectTimeout(params.getConnectTimeout(),
					TimeUnit.MILLISECONDS);
			clientBuilder.readTimeout(params.getReadTimeout(),
					TimeUnit.MILLISECONDS);
			if (params.getProxy() != null) {
				clientBuilder.proxy(params.getProxy());
			}
			if (params.getSSLContext() != null) {
				clientBuilder.sslSocketFactory(params.getSSLContext()
						.getSocketFactory(),
						HttpClientFactory.AllowX509TrustManager.GLOBAL);
			}
			if (params.getHostnameVerifier() != null) {
				clientBuilder.hostnameVerifier(params.getHostnameVerifier());
			}
		}
	}

	public OkHttpClient3Factory setWriteTimeout(int writeTimeout) {
		clientBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
		return this;
	}

	public OkHttpClient3Factory addInterceptor(Interceptor interceptor) {
		clientBuilder.addInterceptor(interceptor);
		return this;
	}

	public OkHttpClient3Factory retryOnConnectionFailure(
			boolean retryOnConnectionFailure) {
		clientBuilder.retryOnConnectionFailure(retryOnConnectionFailure);
		return this;
	}

	public OkHttpClient3Factory setCookieJar(CookieJar cookieJar) {
		clientBuilder.cookieJar(cookieJar);
		return this;
	}

	public OkHttpClient3Factory setCache(Cache cache) {
		clientBuilder.cache(cache);
		return this;
	}

	public OkHttpClient3Factory setConnectionPool(ConnectionPool connectionPool) {
		clientBuilder.connectionPool(connectionPool);
		return this;
	}

	public OkHttpClient3Factory setDispatcher(Dispatcher dispatcher) {
		clientBuilder.dispatcher(dispatcher);
		return this;
	}

	public OkHttpClient3Factory setDns(Dns dns) {
		clientBuilder.dns(dns);
		return this;
	}

	@Override
	public HttpClient newInstance(HttpParams params) {
		resolveHttpParams(params);
		if (okClient == null) {
			okClient = clientBuilder.build();
		}
		return new OkHttpClient3(okClient);
	}
}
