package com.foxinmy.weixin4j.http.factory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.protocol.Protocol;

import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.http.HttpVersion;

/**
 * HttpComponent3 Response
 * 
 * @className HttpComponent3Response
 * @author jy
 * @date 2015年8月17日
 * @since JDK 1.7
 * @see
 */
public class HttpComponent3Response implements HttpResponse {

	private final HttpMethod httpMethod;

	private HttpHeaders headers;
	private HttpVersion protocol;
	private HttpStatus status;
	private InputStream body;

	public HttpComponent3Response(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
		try {
			this.body = new ByteArrayInputStream(httpMethod.getResponseBody());
		} catch (IOException e) {
			;
		}
	}

	@Override
	public HttpHeaders getHeaders() {
		if (headers == null) {
			headers = new HttpHeaders();
			Header[] headers = httpMethod.getResponseHeaders();
			for (Header header : headers) {
				this.headers.add(header.getName(), header.getValue());
			}
		}
		return headers;
	}

	@Override
	public HttpVersion getProtocol() {
		org.apache.commons.httpclient.HttpVersion version = httpMethod
				.getParams().getVersion();
		if (version == null) {
			return null;
		}
		Header connection = httpMethod.getResponseHeader("Connection");
		if (protocol == null) {
			protocol = new HttpVersion("HTTP", version.getMinor(),
					version.getMajor(), connection != null
							&& "keep-alive".equalsIgnoreCase(connection
									.getValue()));
		}
		return protocol;
	}

	@Override
	public HttpStatus getStatus() throws HttpClientException {
		if (status == null) {
			status = new HttpStatus(httpMethod.getStatusCode(),
					httpMethod.getStatusText());
		}
		return status;
	}

	@Override
	public InputStream getBody() throws HttpClientException {
		return body;
	}

	@Override
	public void close() {
		httpMethod.releaseConnection();
		Protocol.unregisterProtocol("https");
	}
}
