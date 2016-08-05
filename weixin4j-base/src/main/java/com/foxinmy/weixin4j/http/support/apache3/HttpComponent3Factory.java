package com.foxinmy.weixin4j.http.support.apache3;

import java.net.InetSocketAddress;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;

import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.support.apache3.HttpComponent3.SSLProtocolSocketFactory;
import com.foxinmy.weixin4j.util.Consts;

/**
 * 使用commons-httpclient3.x
 *
 * @className HttpComponent3Factory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月12日
 * @since JDK 1.6
 */
public class HttpComponent3Factory extends HttpClientFactory {

	private final HttpClient httpClient;

	public HttpComponent3Factory() {
		this(new HttpClient());
	}

	public HttpComponent3Factory(HttpClient httpClient) {
		this.httpClient = httpClient;
		this.httpClient.getParams().setHttpElementCharset(Consts.UTF_8.name());
		this.httpClient.getParams().setParameter("http.protocol.uri-charset",
				Consts.UTF_8.name());
		// httpMethod.getParams().setUriCharset(Consts.UTF_8.name());
		this.httpClient.getParams().setContentCharset(Consts.UTF_8.name());
	}

	/**
	 * Resolve Parameter
	 */
	@Override
	protected void resolveHttpParams(HttpParams params) {
		if (params.getProxy() != null) {
			InetSocketAddress socketAddress = (InetSocketAddress) params
					.getProxy().address();
			httpClient.getHostConfiguration().setProxy(
					socketAddress.getHostName(), socketAddress.getPort());
		}
		if (params.getSSLContext() != null) {
			Protocol.registerProtocol("https", new Protocol("https",
					new SSLProtocolSocketFactory(params.getSSLContext()), 443));
		}
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(params.getConnectTimeout());
	}

	public void setHttpConnectionManager(
			HttpConnectionManager httpConnectionManager) {
		if (httpConnectionManager == null) {
			throw new IllegalArgumentException(
					"'httpConnectionManager' must not be null");
		}
		httpClient.setHttpConnectionManager(httpConnectionManager);
	}

	public void setHttpClientParams(HttpClientParams httpClientParams) {
		if (httpClientParams == null) {
			throw new IllegalArgumentException(
					"'httpClientParams' must not be null");
		}
		httpClient.setParams(httpClientParams);
	}

	@Override
	public com.foxinmy.weixin4j.http.HttpClient newInstance() {
		return new HttpComponent3(httpClient);
	}
}
