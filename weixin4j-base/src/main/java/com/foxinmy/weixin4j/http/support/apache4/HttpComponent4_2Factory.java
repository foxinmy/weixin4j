package com.foxinmy.weixin4j.http.support.apache4;

import java.net.InetSocketAddress;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpProcessor;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.support.apache4.HttpComponent4.CustomHostnameVerifier;
import com.foxinmy.weixin4j.util.Consts;

/**
 * 使用Apache的HttpClient>=4.3
 * 
 * @className HttpComponent4Factory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月12日
 * @since JDK 1.6
 */
public class HttpComponent4_2Factory extends HttpClientFactory {

	private volatile CloseableHttpClient httpClient;
	private final HttpClientBuilder clientBuilder;

	public HttpComponent4_2Factory() {
		this(HttpClients.custom().setDefaultConnectionConfig(
				ConnectionConfig.custom().setCharset(Consts.UTF_8).build()));
	}

	public HttpComponent4_2Factory(HttpClientBuilder clientBuilder) {
		this.clientBuilder = clientBuilder;
	}

	@Override
	public void resolveHttpParams(HttpParams params) {
		clientBuilder.setDefaultRequestConfig(RequestConfig.custom()
				.setConnectTimeout(params.getConnectTimeout())
				.setConnectionRequestTimeout(params.getReadTimeout()).build());
		if (params.getProxy() != null) {
			InetSocketAddress socketAddress = (InetSocketAddress) params
					.getProxy().address();
			HttpHost proxy = new HttpHost(socketAddress.getHostName(),
					socketAddress.getPort());
			clientBuilder.setProxy(proxy);
		}
		if (params.getHostnameVerifier() != null) {
			clientBuilder.setHostnameVerifier(new CustomHostnameVerifier(params
					.getHostnameVerifier()));
		}
		if (params.getSSLContext() != null) {
			clientBuilder.setSslcontext(params.getSSLContext());
		}
	}

	public HttpComponent4_2Factory setDefaultConnectionConfig(
			ConnectionConfig connectionConfig) {
		clientBuilder.setDefaultConnectionConfig(connectionConfig);
		return this;
	}

	public HttpComponent4_2Factory setDefaultSocketConfig(
			SocketConfig socketConfig) {
		clientBuilder.setDefaultSocketConfig(socketConfig);
		return this;
	}

	public HttpComponent4_2Factory setConnectionManager(
			HttpClientConnectionManager connectionManager) {
		clientBuilder.setConnectionManager(connectionManager);
		return this;
	}

	public HttpComponent4_2Factory setHttpProcessor(HttpProcessor httpprocessor) {
		clientBuilder.setHttpProcessor(httpprocessor);
		return this;
	}

	public HttpComponent4_2Factory retryHandler(
			HttpRequestRetryHandler retryHandler) {
		clientBuilder.setRetryHandler(retryHandler);
		return this;
	}

	@Override
	public HttpClient newInstance() {
		if (httpClient == null) {
			this.httpClient = clientBuilder.build();
		}
		return new HttpComponent4_2(httpClient);
	}
}
