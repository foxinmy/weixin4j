package com.foxinmy.weixin4j.http.support.apache4;

import java.net.InetSocketAddress;

import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

import com.foxinmy.weixin4j.http.HttpClient;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.http.support.apache4.HttpComponent4.CustomHostnameVerifier;
import com.foxinmy.weixin4j.util.Consts;

/**
 * 使用Apache的HttpClient<=4.2
 * 
 * @className HttpComponent4Factory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月12日
 * @since JDK 1.6
 */
public class HttpComponent4_1Factory extends HttpClientFactory {

	private final org.apache.http.client.HttpClient httpClient;

	/**
	 * 默认httpclient
	 * @see <a href="https://issues.apache.org/jira/browse/HTTPCLIENT-1193">HTTPCLIENT-1193</a>
	 * @param clientConnectionManager
	 */
	public HttpComponent4_1Factory() {
		PoolingClientConnectionManager clientConnectionManager = new PoolingClientConnectionManager();
		clientConnectionManager.setMaxTotal(30);
		clientConnectionManager.setDefaultMaxPerRoute(clientConnectionManager.getMaxTotal());
		this.httpClient = new DefaultHttpClient(clientConnectionManager);
	}

	public HttpComponent4_1Factory(org.apache.http.client.HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	protected void resolveHttpParams0(HttpParams params) {
		if (params.getProxy() != null) {
			InetSocketAddress socketAddress = (InetSocketAddress) params
					.getProxy().address();
			HttpHost proxy = new HttpHost(socketAddress.getHostName(),
					socketAddress.getPort());
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}
		httpClient.getParams().setIntParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT,
				params.getConnectTimeout());
		httpClient.getParams().setIntParameter(
				CoreConnectionPNames.SO_TIMEOUT,
				params.getReadTimeout());
		httpClient.getParams().setParameter(
				CoreProtocolPNames.HTTP_CONTENT_CHARSET, Consts.UTF_8);
		httpClient.getParams().setParameter(
				CoreProtocolPNames.HTTP_ELEMENT_CHARSET, Consts.UTF_8.name());
		httpClient.getParams().setParameter(
				CoreProtocolPNames.STRICT_TRANSFER_ENCODING, Consts.UTF_8);
		httpClient.getParams().setParameter(HttpHeaders.CONTENT_ENCODING,
				Consts.UTF_8);
		httpClient.getParams().setParameter(HttpHeaders.ACCEPT_CHARSET,
				Consts.UTF_8);
		if (params.getSSLContext() != null) {
			X509HostnameVerifier hostnameVerifier = null;
			if (params.getHostnameVerifier() != null) {
				hostnameVerifier = new CustomHostnameVerifier(
						params.getHostnameVerifier());
			}
			if (hostnameVerifier == null) {
				hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			}
			SSLSocketFactory socketFactory = new SSLSocketFactory(
					params.getSSLContext());
			socketFactory.setHostnameVerifier(hostnameVerifier);
			Scheme scheme = new Scheme("https", socketFactory, 443);
			httpClient.getConnectionManager().getSchemeRegistry()
					.register(scheme);
		}
	}

	/**
	 * 设置Http参数
	 * 
	 * @see org.apache.http.params.HttpParams
	 * @param name
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setParameter(String name, Object value) {
		httpClient.getParams().setParameter(name, value);
	}

	@Override
	public HttpClient newInstance() {
		return new HttpComponent4_1(httpClient);
	}
}
