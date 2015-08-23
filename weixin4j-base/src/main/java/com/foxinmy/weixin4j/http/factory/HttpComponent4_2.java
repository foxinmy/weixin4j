package com.foxinmy.weixin4j.http.factory;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.foxinmy.weixin4j.http.HttpClient;
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
 * @since JDK 1.7
 * @see
 */
public class HttpComponent4_2 extends HttpComponent4 implements HttpClient {

	private CloseableHttpClient httpClient;

	public HttpComponent4_2() {
		this.httpClient = HttpClients.createDefault();
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			HttpRequestBase uriRequest = methodMap.get(request.getMethod());
			uriRequest.setURI(request.getURI());
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
				}
				if (params.getSSLContext() != null) {
					X509HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
					if (params.getHostnameVerifier() != null) {
						hostnameVerifier = new CustomHostnameVerifier(
								params.getHostnameVerifier());
					}
					httpClient = HttpClients.custom()
							.setHostnameVerifier(hostnameVerifier)
							.setSslcontext(params.getSSLContext()).build();
				}
				uriRequest.setConfig(requestConfig.build());
			}
			addHeaders(request.getHeaders(), uriRequest);
			addEntity(request.getEntity(), uriRequest);
			CloseableHttpResponse httpResponse = httpClient.execute(uriRequest);
			response = new HttpComponent4_2Response(httpResponse);
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
