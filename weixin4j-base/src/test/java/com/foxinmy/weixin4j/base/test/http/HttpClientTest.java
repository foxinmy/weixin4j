package com.foxinmy.weixin4j.base.test.http;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.URLParameter;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.support.netty.Netty4HttpClientFactory;

/**
 * HttpClient Test
 * 
 * @className HttpClientTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月28日
 * @since JDK 1.6
 */
public abstract class HttpClientTest {

	private static final String GET_URL = "http://www.iteye.com/";
	private static final String POST_URL = "http://223.72.192.176:8080/YuJia/LoginServlet";
	private static final String HTTPS_URL = "https://www.baidu.com/";
	protected static final Proxy PROXY = new Proxy(Type.HTTP,
			new InetSocketAddress("219.141.225.108", 80));

	protected abstract HttpClientFactory createHttpFactory();

	protected HttpClient createHttpClient() {
		return createHttpFactory().newInstance(null);
	}

	protected HttpClient createProxyHttpClient() {
		HttpParams params = new HttpParams();
		params.setProxy(PROXY);
		return createHttpClient(params);
	}

	protected HttpClient createSSLHttpClient() {
		HttpParams params = new HttpParams();
		params.setHostnameVerifier(HttpClientFactory.AllowHostnameVerifier.GLOBAL);
		params.setSSLContext(HttpClientFactory.allowSSLContext());
		return createHttpClient(params);
	}

	protected HttpClient createProxyAndSSLHttpClient() {
		HttpParams params = new HttpParams();
		params.setHostnameVerifier(HttpClientFactory.AllowHostnameVerifier.GLOBAL);
		params.setSSLContext(HttpClientFactory.allowSSLContext());
		params.setProxy(PROXY);
		return createHttpClient(params);
	}

	protected HttpClient createHttpClient(HttpParams params) {
		HttpClientFactory httpClientFactory = createHttpFactory();
		HttpClientFactory.setDefaultParams(params);
		return httpClientFactory.newInstance(null);
	}

	@Test
	public void getDefaultHttpClientFactoryTest() {
		HttpClientFactory httpClientFactory = HttpClientFactory
				.getDefaultFactory();
		Assert.assertTrue(httpClientFactory.getClass().isAssignableFrom(
				Netty4HttpClientFactory.class));
	}

	@Test
	public void getRequestTest() throws HttpClientException {
		HttpClient httpClient = createHttpClient();
		HttpResponse response = httpClient.get(GET_URL);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
	}

	@Test
	public void postRequestTest() throws HttpClientException {
		HttpClient httpClient = createHttpClient();
		URLParameter parameter = new URLParameter("query", "java");
		HttpResponse response = httpClient.post(POST_URL, parameter);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
	}

	@Test
	public void httpsRequestTest() throws HttpClientException {
		HttpClient httpClient = createHttpClient();
		HttpResponse response = httpClient.get(HTTPS_URL);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
	}

	@Test
	public void proxyRequestTest() throws HttpClientException {
		HttpClient httpClient = createProxyHttpClient();
		HttpResponse response = httpClient.get(GET_URL);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
		response = httpClient.get(HTTPS_URL);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
	}

	@Test
	public void sslRequestTest() throws HttpClientException {
		HttpClient httpClient = createSSLHttpClient();
		HttpResponse response = httpClient.get(GET_URL);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
		response = httpClient.get(HTTPS_URL);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
	}

	@Test
	public void proxyAndSSLRequestTest() throws HttpClientException {
		HttpClient httpClient = createProxyAndSSLHttpClient();
		HttpResponse response = httpClient.get(GET_URL);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
		response = httpClient.get(HTTPS_URL);
		Assert.assertEquals(200, response.getStatus().getStatusCode());
	}
}
