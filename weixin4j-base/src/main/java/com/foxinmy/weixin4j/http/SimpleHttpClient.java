package com.foxinmy.weixin4j.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.http.factory.HttpClientFactory;
import com.foxinmy.weixin4j.util.IOUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * HTTP 简单实现
 * 
 * @className SimpleHttpClient
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.6
 * @see
 */
public class SimpleHttpClient extends AbstractHttpClient implements HttpClient {

	protected HostnameVerifier createHostnameVerifier() {
		return new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
	}

	protected HttpURLConnection createHttpConnection(HttpRequest request)
			throws IOException {
		URI uri = request.getURI();
		HttpParams params = request.getParams();
		Proxy proxy = params != null ? params.getProxy() : null;
		URLConnection urlConnection = proxy != null ? uri.toURL()
				.openConnection(proxy) : uri.toURL().openConnection();
		if (uri.getScheme().equals("https")) {
			try {
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
					hostnameVerifier = createHostnameVerifier();
				}
				HttpsURLConnection connection = (HttpsURLConnection) urlConnection;
				connection.setSSLSocketFactory(sslContext.getSocketFactory());
				connection.setHostnameVerifier(hostnameVerifier);
				return connection;
			} catch (HttpClientException e) {
				throw new IOException(e);
			}
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
			// set parameters
			HttpParams params = request.getParams();
			if (params != null) {
				connection.setAllowUserInteraction(params
						.isAllowUserInteraction());
				connection.setConnectTimeout(params.getConnectTimeout());
				connection.setReadTimeout(params.getReadTimeout());
				connection.setIfModifiedSince(params.getIfModifiedSince());
				connection.setInstanceFollowRedirects(params
						.isFollowRedirects());
			}
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
			for (Iterator<Entry<String, List<String>>> headerIterator = headers
					.entrySet().iterator(); headerIterator.hasNext();) {
				Entry<String, List<String>> header = headerIterator.next();
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
				// Read Out Exception when connection.disconnect();
				/*
				 * if (httpEntity.getContentLength() > 0l) {
				 * connection.setFixedLengthStreamingMode(httpEntity
				 * .getContentLength()); } else { connection
				 * .setChunkedStreamingMode(params != null ? params
				 * .getChunkSize() : 4096); }
				 */
				if (httpEntity.getContentLength() > 0l) {
					connection.setRequestProperty(HttpHeaders.CONTENT_LENGTH,
							Long.toString(httpEntity.getContentLength()));
				}
				if (httpEntity.getContentType() != null) {
					connection.setRequestProperty(HttpHeaders.CONTENT_TYPE,
							httpEntity.getContentType().getMimeType());
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
					+ request.getURI().toString() + "\":" + e.getMessage(), e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return response;
	}
}
