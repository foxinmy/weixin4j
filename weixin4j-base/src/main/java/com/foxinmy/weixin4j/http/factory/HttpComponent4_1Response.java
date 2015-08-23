package com.foxinmy.weixin4j.http.factory;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

import com.foxinmy.weixin4j.http.HttpClientException;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.http.HttpVersion;

/**
 * HttpComponent4 Response:Requires Apache HttpComponents 4.2 or lower
 * 
 * @className HttpComponent4_1Response
 * @author jy
 * @date 2015年8月18日
 * @since JDK 1.7
 * @see
 */
public class HttpComponent4_1Response implements HttpResponse {
	private final org.apache.http.client.HttpClient httpClient;
	private final org.apache.http.HttpResponse httpResponse;

	private HttpHeaders headers;
	private HttpVersion protocol;
	private HttpStatus status;

	public HttpComponent4_1Response(
			org.apache.http.client.HttpClient httpClient,
			org.apache.http.HttpResponse httpResponse) {
		this.httpClient = httpClient;
		this.httpResponse = httpResponse;
	}

	@Override
	public HttpHeaders getHeaders() {
		if (headers == null) {
			headers = new HttpHeaders();
			Header[] headers = httpResponse.getAllHeaders();
			for (Header header : headers) {
				this.headers.add(header.getName(), header.getValue());
			}
		}
		return headers;
	}

	@Override
	public HttpVersion getProtocol() {
		if (protocol == null) {
			ProtocolVersion version = httpResponse.getProtocolVersion();
			Header connection = httpResponse.getFirstHeader("Connection");
			protocol = new HttpVersion(version.getProtocol(),
					version.getMajor(), version.getMinor(), connection != null
							&& connection.getValue().equalsIgnoreCase(
									"keep-alive"));
		}
		return protocol;
	}

	@Override
	public HttpStatus getStatus() throws HttpClientException {
		if (status == null) {
			StatusLine statusLine = httpResponse.getStatusLine();
			status = new HttpStatus(statusLine.getStatusCode(),
					statusLine.getReasonPhrase());
		}
		return status;
	}

	@Override
	public InputStream getBody() throws HttpClientException {
		try {
			HttpEntity entity = this.httpResponse.getEntity();
			return (entity != null ? entity.getContent() : null);
		} catch (IOException e) {
			throw new HttpClientException("I/O Error on getBody", e);
		}
	}

	@Override
	public void close() {
		httpClient.getConnectionManager().closeExpiredConnections();
	}
}
