package com.foxinmy.weixin4j.http.support.apache4;

import java.net.InetSocketAddress;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
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

	private final HttpClientBuilder clientBuilder;

	public HttpComponent4_2Factory() {
		clientBuilder = HttpClients.custom().setDefaultConnectionConfig(
				ConnectionConfig.custom().setCharset(Consts.UTF_8).build());
		clientBuilder
				.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		clientBuilder.setSSLSocketFactory(new SSLConnectionSocketFactory(
				HttpClientFactory.allowSSLContext(),
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));
	}

	public HttpComponent4_2Factory(HttpClientBuilder clientBuilder) {
		this.clientBuilder = clientBuilder;
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

	private void resolveHttpParams(HttpParams params) {
		if (params != null) {
			clientBuilder.setDefaultRequestConfig(RequestConfig.custom()
					.setConnectTimeout(params.getConnectTimeout())
					.setConnectionRequestTimeout(params.getReadTimeout())
					.build());
			if (params.getProxy() != null) {
				InetSocketAddress socketAddress = (InetSocketAddress) params
						.getProxy().address();
				HttpHost proxy = new HttpHost(socketAddress.getHostName(),
						socketAddress.getPort());
				clientBuilder.setProxy(proxy);
			}
			if (params.getSSLContext() != null) {
				clientBuilder
						.setSSLSocketFactory(new SSLConnectionSocketFactory(
								params.getSSLContext(),
								params.getHostnameVerifier() != null ? new CustomHostnameVerifier(
										params.getHostnameVerifier())
										: SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));
			}
			if (params.getHostnameVerifier() != null) {
				clientBuilder.setHostnameVerifier(new CustomHostnameVerifier(
						params.getHostnameVerifier()));
			}
		}
	}

	@Override
	public HttpClient newInstance(HttpParams params) {
		resolveHttpParams(params);
		return new HttpComponent4_2(clientBuilder.build());
	}
}
