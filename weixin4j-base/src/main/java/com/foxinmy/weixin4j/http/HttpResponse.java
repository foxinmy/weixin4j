package com.foxinmy.weixin4j.http;

/**
 * HTTP 响应
 * 
 * @className HttpResponse
 * @author jy
 * @date 2015年5月30日
 * @since JDK 1.7
 * @see
 */
public class HttpResponse extends AbstractHttpMessage implements HttpMessage {

	private HttpVersion httpVersion;
	private StatusLine statusLine;
	private byte[] content;

	@Override
	public HttpVersion getProtocolVersion() {
		return httpVersion;
	}

	public StatusLine getStatusLine() {
		return statusLine;
	}

	public byte[] getContent() {
		return content;
	}

	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public HttpVersion getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

	@Override
	public String toString() {
		return "HttpResponse [httpVersion=" + httpVersion + ", statusLine="
				+ statusLine + "]";
	}
}
