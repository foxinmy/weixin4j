package com.foxinmy.weixin4j.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * HTTP 简单实现
 *
 * @className SimpleHttpClient
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月29日
 * @since JDK 1.6
 * @see
 */
public class SimpleHttpClient extends AbstractHttpClient {

	private final HttpParams params;

	public SimpleHttpClient(HttpParams params) {
		this.params = params;
	}

	protected HttpURLConnection createHttpConnection(HttpRequest request)
			throws IOException {
		URI uri = request.getURI();
		Proxy proxy = params != null ? params.getProxy() : null;
		URLConnection urlConnection = proxy != null ? uri.toURL()
				.openConnection(proxy) : uri.toURL().openConnection();
		if (uri.getScheme().equals("https")) {
			SSLContext sslContext = null;
			HostnameVerifier hostnameVerifier = null;
			if (params != null) {
				sslContext = params.getSSLContext();
				hostnameVerifier = params.getHostnameVerifier();
			}
			if (sslContext == null) {
				sslContext = HttpClientFactory.allowSSLContext();
			}
			if (hostnameVerifier == null) {
				hostnameVerifier = HttpClientFactory.AllowHostnameVerifier.GLOBAL;
			}
			HttpsURLConnection connection = (HttpsURLConnection) urlConnection;
			connection.setSSLSocketFactory(sslContext.getSocketFactory());
			connection.setHostnameVerifier(hostnameVerifier);
			return connection;
		} else {
			return (HttpURLConnection) urlConnection;
		}
	}

	@Override
	public HttpResponse execute(HttpRequest request) throws HttpClientException {
		HttpResponse response = null;
		try {
			// create connection object
			HttpURLConnection connection = createHttpConnection(request);
			String method = request.getMethod().name();
			connection.setRequestMethod(method);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects("GET".equals(method));
			if ("PUT".equals(method) || "POST".equals(method)
					|| "PATCH".equals(method) || "DELETE".equals(method)) {
				connection.setDoOutput(true);
			} else {
				connection.setDoOutput(false);
			}
			// set headers
			HttpHeaders headers = request.getHeaders();
			if (headers == null) {
				headers = new HttpHeaders();
			}
			if (!headers.containsKey(HttpHeaders.HOST)) {
				headers.set(HttpHeaders.HOST, request.getURI().getHost());
			}
			// Add default accept headers
			if (!headers.containsKey(HttpHeaders.ACCEPT)) {
				headers.set(HttpHeaders.ACCEPT, "*/*");
			}
			// Add default user agent
			if (!headers.containsKey(HttpHeaders.USER_AGENT)) {
				headers.set(HttpHeaders.USER_AGENT, "jdk/httpclient");
			}
			for (Entry<String, List<String>> header : headers.entrySet()) {
				if (HttpHeaders.COOKIE.equalsIgnoreCase(header.getKey())) {
					connection.setRequestProperty(header.getKey(),
							StringUtil.join(header.getValue(), ';'));
				} else {
					for (String headerValue : header.getValue()) {
						connection.addRequestProperty(header.getKey(),
								headerValue != null ? headerValue : "");
					}
				}
			}
			// set inputstream
			HttpEntity httpEntity = request.getEntity();
			if (httpEntity != null) {
				connection.setUseCaches(false);
				if (httpEntity.getContentLength() > 0l) {
					connection.setRequestProperty(HttpHeaders.CONTENT_LENGTH,
							Long.toString(httpEntity.getContentLength()));
				}
				if (httpEntity.getContentType() != null) {
					connection.setRequestProperty(HttpHeaders.CONTENT_TYPE,
							httpEntity.getContentType().toString());
				}
			}
			// connect
			connection.connect();
			// open stream
			if (httpEntity != null) {
				OutputStream output = connection.getOutputStream();
				httpEntity.writeTo(output);
				output.flush();
				output.close();
			}
			// building response
			InputStream input = connection.getErrorStream() != null ? connection
					.getErrorStream() : connection.getInputStream();
			byte[] content = IOUtil.toByteArray(input);
			response = new SimpleHttpResponse(connection, content);
			input.close();
			handleResponse(response);
		} catch (IOException e) {
			throw new HttpClientException("I/O error on "
					+ request.getMethod().name() + " request for \""
					+ request.getURI().toString(), e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return response;
	}
}
