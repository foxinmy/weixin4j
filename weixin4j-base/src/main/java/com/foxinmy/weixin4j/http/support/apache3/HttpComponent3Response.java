package com.foxinmy.weixin4j.http.support.apache3;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;

import com.foxinmy.weixin4j.http.AbstractHttpResponse;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.http.HttpVersion;

/**
 * HttpComponent3 Response:Requires commons-httpclient 3.0 or higher
 *
 * @className HttpComponent3Response
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月17日
 * @since JDK 1.6
 * @see
 */
public class HttpComponent3Response extends AbstractHttpResponse {

	private final HttpMethod httpMethod;

	private HttpHeaders headers;
	private HttpVersion protocol;
	private HttpStatus status;

	public HttpComponent3Response(HttpMethod httpMethod) throws IOException {
		super(httpMethod.getResponseBody());
		this.httpMethod = httpMethod;
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
			protocol = new HttpVersion("HTTP", version.getMajor(),
					version.getMinor(), connection != null
							&& KEEP_ALIVE.equalsIgnoreCase(connection
									.getValue()));
		}
		return protocol;
	}

	@Override
	public HttpStatus getStatus() {
		if (status == null) {
			status = new HttpStatus(httpMethod.getStatusCode(),
					httpMethod.getStatusText());
		}
		return status;
	}

	@Override
	public void close() {
		httpMethod.releaseConnection();
		//Protocol.unregisterProtocol("https");
	}
}
