package com.foxinmy.weixin4j.http;

import java.net.URI;

/**
 * GET 请求
 * @className HttpGet
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public class HttpGet extends AbstractHttpRequest {

	private final URI uri;

	public HttpGet(final URI uri) {
		this.uri = uri;
	}

	public HttpGet(final String uri) {
		this(URI.create(uri));
	}

	@Override
	public URI getURI() {
		return this.uri;
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.GET;
	}
}
