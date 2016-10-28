package com.foxinmy.weixin4j.http.support.apache4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.util.EntityUtils;

import com.foxinmy.weixin4j.http.AbstractHttpClient;
import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpMethod;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.apache.MultipartEntity;
import com.foxinmy.weixin4j.http.entity.HttpEntity;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * Apache HttpComponents 4.x
 * 
 * @className HttpComponent4
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月18日
 * @since JDK 1.6
 * @see
 */
public abstract class HttpComponent4 extends AbstractHttpClient {

	/**
	 * Create HttpRequest
	 */
	protected HttpRequestBase createRequest(HttpRequest request)
			throws HttpClientException, IOException {
		HttpRequestBase httpRequest = createMethod(request);
		resolveHeaders(request.getHeaders(), httpRequest);
		resolveContent(request.getEntity(), httpRequest);
		return httpRequest;
	}

	/**
	 * Create HttpMethod
	 */
	protected HttpRequestBase createMethod(HttpRequest request)
			throws HttpClientException {
		HttpMethod method = request.getMethod();
		URI uri = request.getURI();
		if (method == HttpMethod.GET) {
			return new HttpGet(uri);
		} else if (method == HttpMethod.HEAD) {
			return new HttpHead(uri);
		} else if (method == HttpMethod.POST) {
			return new HttpPost(uri);
		} else if (method == HttpMethod.PUT) {
			return new HttpPut(uri);
		} else if (method == HttpMethod.DELETE) {
			return new HttpDelete(uri);
		} else if (method == HttpMethod.OPTIONS) {
			return new HttpOptions(uri);
		} else if (method == HttpMethod.TRACE) {
			return new HttpTrace(uri);
		} else {
			throw new HttpClientException("unknown request method " + method
					+ " for " + uri);
		}
	}

	/**
	 * Resolve Headers
	 */
	protected void resolveHeaders(HttpHeaders headers,
			HttpRequestBase httpRequest) {
		if (headers == null) {
			headers = new HttpHeaders();
		}
		// Add default accept headers
		if (!headers.containsKey(HttpHeaders.ACCEPT)) {
			headers.set(HttpHeaders.ACCEPT, "*/*");
		}
		// Add default user agent
		if (!headers.containsKey(HttpHeaders.USER_AGENT)) {
			headers.set(HttpHeaders.USER_AGENT, "apache/httpclient4");
		}
		for (Entry<String, List<String>> header : headers.entrySet()) {
			if (HttpHeaders.COOKIE.equalsIgnoreCase(header.getKey())) {
				httpRequest.setHeader(header.getKey(),
						StringUtil.join(header.getValue(), ';'));
			} else {
				for (String headerValue : header.getValue()) {
					httpRequest.setHeader(header.getKey(),
							headerValue != null ? headerValue : "");
				}
			}
		}
	}

	/**
	 * Resolve Content
	 */
	protected void resolveContent(HttpEntity entity, HttpRequestBase httpRequest)
			throws IOException {
		if (entity != null) {
			AbstractHttpEntity httpEntity = null;
			if (entity instanceof MultipartEntity) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				entity.writeTo(os);
				os.flush();
				httpEntity = new org.apache.http.entity.ByteArrayEntity(
						os.toByteArray());
				os.close();
			} else {
				httpEntity = new InputStreamEntity(entity.getContent(),
						entity.getContentLength());
			}
			httpEntity.setContentType(entity.getContentType().toString());
			((HttpEntityEnclosingRequestBase) httpRequest)
					.setEntity(httpEntity);
		}
	}

	protected byte[] getContent(org.apache.http.HttpResponse httpResponse)
			throws IOException {
		return EntityUtils.toByteArray(httpResponse.getEntity());
	}

	protected static class CustomHostnameVerifier implements
			X509HostnameVerifier {

		private final HostnameVerifier hostnameVerifier;

		public CustomHostnameVerifier(HostnameVerifier hostnameVerifier) {
			this.hostnameVerifier = hostnameVerifier;
		}

		@Override
		public boolean verify(String hostname, SSLSession session) {
			return hostnameVerifier.verify(hostname, session);
		}

		@Override
		public void verify(String host, SSLSocket ssl) throws IOException {
		}

		@Override
		public void verify(String host, X509Certificate cert)
				throws SSLException {
		}

		@Override
		public void verify(String host, String[] cns, String[] subjectAlts)
				throws SSLException {
		}
	}
}
