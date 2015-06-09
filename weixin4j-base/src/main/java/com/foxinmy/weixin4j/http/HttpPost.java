package com.foxinmy.weixin4j.http;

import java.net.URI;

import com.foxinmy.weixin4j.http.entity.HttpEntity;

/**
 * POST 请求
 * 
 * @className HttpPost
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public class HttpPost extends HttpEntityRequest {

	private final URI uri;

	public HttpPost(final URI uri) {
		this.uri = uri;
	}

	public HttpPost(final String uri) {
		this(URI.create(uri));
	}

	@Override
	public HttpMethod getMethod() {
		return HttpMethod.POST;
	}

	@Override
	public URI getURI() {
		return this.uri;
	}

	private HttpEntity entity;

	@Override
	public HttpEntity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}
}
