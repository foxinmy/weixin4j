package com.foxinmy.weixin4j.http.support.apache4;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.foxinmy.weixin4j.http.AbstractHttpResponse;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.http.HttpVersion;

/**
 * HttpComponent4 Response:Requires Apache HttpComponents 4.3 or higher
 * 
 * @className HttpComponent4_2Response
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年8月18日
 * @since JDK 1.6
 * @see
 */
public class HttpComponent4_2Response extends AbstractHttpResponse {

	private final CloseableHttpResponse httpResponse;

	private HttpHeaders headers;
	private HttpVersion protocol;
	private HttpStatus status;

	public HttpComponent4_2Response(CloseableHttpResponse httpResponse,
			byte[] content) {
		super(content);
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
							&& KEEP_ALIVE.equalsIgnoreCase(connection
									.getValue()));
		}
		return protocol;
	}

	@Override
	public HttpStatus getStatus() {
		if (status == null) {
			StatusLine statusLine = httpResponse.getStatusLine();
			status = new HttpStatus(statusLine.getStatusCode(),
					statusLine.getReasonPhrase());
		}
		return status;
	}

	@Override
	public void close() {
		try {
			EntityUtils.consume(httpResponse.getEntity());
			httpResponse.close();
		} catch (IOException ex) {
			;
		}
	}
}
