package com.foxinmy.weixin4j.http.factory;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;

/**
 * Requires Apache HttpComponents 4.3 or higher
 * 
 * @className HttpComponent4_2
 * @author jy
 * @date 2015年8月18日
 * @since JDK 1.6
 * @see
 */
public class HttpComponent4_2 extends HttpComponent4 {

	private CloseableHttpClient httpClient;

	public HttpComponent4_2() {
		this.httpClient = HttpClients.createDefault();
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			HttpRequestBase uriRequest = createHttpRequest(request.getMethod(),
					request.getURI());
			boolean useSSL = "https".equals(request.getURI().getScheme());
			SSLContext sslContext = null;
			HostnameVerifier hostnameVerifier = null;
			HttpParams params = request.getParams();
			if (params != null) {
				Builder requestConfig = RequestConfig.custom()
						.setSocketTimeout(params.getSocketTimeout())
						.setConnectTimeout(params.getConnectTimeout())
						.setConnectionRequestTimeout(params.getReadTimeout());
				if (params.getProxy() != null) {
					InetSocketAddress socketAddress = (InetSocketAddress) params
							.getProxy().address();
					HttpHost proxy = new HttpHost(socketAddress.getHostName(),
							socketAddress.getPort());
					requestConfig.setProxy(proxy);
					useSSL = false;
				}
				uriRequest.setConfig(requestConfig.build());
				sslContext = params.getSSLContext();
				hostnameVerifier = params.getHostnameVerifier();
			}
			if (useSSL) {
				if (sslContext == null) {
					sslContext = HttpClientFactory.allowSSLContext();
				}
				X509HostnameVerifier x509HostnameVerifier = null;
				if (hostnameVerifier == null) {
					x509HostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
				} else {
					x509HostnameVerifier = new CustomHostnameVerifier(
							hostnameVerifier);
				}
				httpClient = HttpClients.custom()
						.setHostnameVerifier(x509HostnameVerifier)
						.setSslcontext(sslContext).build();
			}
			addHeaders(request.getHeaders(), uriRequest);
			addEntity(request.getEntity(), uriRequest);
			CloseableHttpResponse httpResponse = httpClient.execute(uriRequest);
			response = new HttpComponent4_2Response(httpResponse,
					getContent(httpResponse));
			handleResponse(response);
		} catch (IOException e) {
			throw new HttpClientException("I/O error on "
					+ request.getMethod().name() + " request for \""
					+ request.getURI().toString() + "\":" + e.getMessage(), e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return response;
	}
}
