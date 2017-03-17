package com.foxinmy.weixin4j.http.support.okhttp;

import java.net.CookieHandler;
import java.util.concurrent.TimeUnit;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.Dns;
import com.squareup.okhttp.OkHttpClient;

/**
 * 使用OkHttp2
 * 
 * @className OkHttpClient2Factory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月25日
 * @since JDK 1.6
 */
public class OkHttpClient2Factory extends HttpClientFactory {
	private final OkHttpClient okClient;

	public OkHttpClient2Factory() {
		okClient = new OkHttpClient();
		okClient.setHostnameVerifier(HttpClientFactory.AllowHostnameVerifier.GLOBAL);
		okClient.setSslSocketFactory(HttpClientFactory.allowSSLContext()
				.getSocketFactory());

	}

	public OkHttpClient2Factory(OkHttpClient okClient) {
		this.okClient = okClient;
	}

	private void resolveHttpParams(HttpParams params) {
		if (params != null) {
			okClient.setConnectTimeout(params.getConnectTimeout(),
					TimeUnit.MILLISECONDS);
			okClient.setReadTimeout(params.getReadTimeout(),
					TimeUnit.MILLISECONDS);
			if (params.getProxy() != null) {
				okClient.setProxy(params.getProxy());
			}
			if (params.getSSLContext() != null) {
				okClient.setSslSocketFactory(params.getSSLContext()
						.getSocketFactory());
			}
			if (params.getHostnameVerifier() != null) {
				okClient.setHostnameVerifier(params.getHostnameVerifier());
			}
		}
	}

	public OkHttpClient2Factory setWriteTimeout(int writeTimeout) {
		okClient.setWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS);
		return this;
	}

	public OkHttpClient2Factory setCache(Cache cache) {
		okClient.setCache(cache);
		return this;
	}

	public OkHttpClient2Factory setConnectionPool(ConnectionPool connectionPool) {
		okClient.setConnectionPool(connectionPool);
		return this;
	}

	public OkHttpClient2Factory setCookieHandler(CookieHandler cookieHandler) {
		okClient.setCookieHandler(cookieHandler);
		return this;
	}

	public OkHttpClient2Factory setDispatcher(Dispatcher dispatcher) {
		okClient.setDispatcher(dispatcher);
		return this;
	}

	public OkHttpClient2Factory setDns(Dns dns) {
		okClient.setDns(dns);
		return this;
	}

	@Override
	public HttpClient newInstance(HttpParams params) {
		resolveHttpParams(params);
		return new OkHttpClient2(okClient);
	}
}
