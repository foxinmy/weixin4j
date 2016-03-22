package com.foxinmy.weixin4j.http.factory;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpParams;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.model.Consts;

/**
 * Requires Apache HttpComponents 4.2 or lower
 * 
 * @className HttpComponent4_1
 * @author jy
 * @date 2015年8月18日
 * @since JDK 1.6
 * @see
 */
public class HttpComponent4_1 extends HttpComponent4 {

	private final org.apache.http.client.HttpClient httpClient;

	public HttpComponent4_1() {
		this.httpClient = new DefaultHttpClient();
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			HttpRequestBase uriRequest = createHttpRequest(request.getMethod(),
					request.getURI());
			boolean useSSL = "https".equals(request.getURI().getScheme());
			SSLContext sslContext = null;
			X509HostnameVerifier hostnameVerifier = null;
			HttpParams params = request.getParams();
			if (params != null) {
				if (params.getProxy() != null) {
					InetSocketAddress socketAddress = (InetSocketAddress) params
							.getProxy().address();
					HttpHost proxy = new HttpHost(socketAddress.getHostName(),
							socketAddress.getPort());
					uriRequest.getParams().setParameter(
							ConnRoutePNames.DEFAULT_PROXY, proxy);
					useSSL = false;
				}
				uriRequest.getParams().setIntParameter(
						CoreConnectionPNames.SOCKET_BUFFER_SIZE,
						params.getChunkSize());
				uriRequest.getParams().setIntParameter(
						CoreConnectionPNames.SO_TIMEOUT,
						params.getSocketTimeout());
				uriRequest.getParams().setIntParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT,
						params.getConnectTimeout());
				uriRequest.getParams().setParameter(
						CoreProtocolPNames.HTTP_CONTENT_CHARSET, Consts.UTF_8);
				uriRequest.getParams().setParameter(
						CoreProtocolPNames.HTTP_ELEMENT_CHARSET, Consts.UTF_8.name());
				uriRequest.getParams().setParameter(
						CoreProtocolPNames.STRICT_TRANSFER_ENCODING, Consts.UTF_8);
				uriRequest.getParams().setParameter(
						HttpHeaders.CONTENT_ENCODING, Consts.UTF_8);
				uriRequest.getParams().setParameter(HttpHeaders.ACCEPT_CHARSET,
						Consts.UTF_8);
				sslContext = params.getSSLContext();
				if (params.getHostnameVerifier() != null) {
					hostnameVerifier = new CustomHostnameVerifier(
							params.getHostnameVerifier());
				}
			}
			if (useSSL) {
				if (sslContext == null) {
					sslContext = HttpClientFactory.allowSSLContext();
				}
				if (hostnameVerifier == null) {
					hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
				}
				SSLSocketFactory socketFactory = new SSLSocketFactory(
						sslContext);
				socketFactory.setHostnameVerifier(hostnameVerifier);
				Scheme scheme = new Scheme("https", socketFactory, 443);
				httpClient.getConnectionManager().getSchemeRegistry()
						.register(scheme);
			}
			addHeaders(request.getHeaders(), uriRequest);
			addEntity(request.getEntity(), uriRequest);
			org.apache.http.HttpResponse httpResponse = httpClient
					.execute(uriRequest);
			response = new HttpComponent4_1Response(httpResponse,
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
