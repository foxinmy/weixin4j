package com.foxinmy.weixin4j.http;

import java.net.URI;

import com.foxinmy.weixin4j.http.entity.HttpEntity;

/**
 * HTTP 请求
 * 
 * @className HttpRequest
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public class HttpRequest implements HttpMessage {
	/**
	 * 请求方式
	 * 
	 * @return
	 */
	private final HttpMethod method;

	/**
	 * 请求路径
	 * 
	 * @return
	 */
	private final URI uri;
	/**
	 * 协议参数
	 * 
	 * @return
	 */
	private HttpParams params;
	/**
	 * 内容参数
	 */
	private HttpEntity entity;
	/**
	 * 请求表头
	 */
	private HttpHeaders headers;

	public HttpRequest(HttpMethod method, URI uri) {
		this.method = method;
		this.uri = uri;
	}

	public HttpRequest(HttpMethod method, String url) {
		this(method, URI.create(url));
	}

	public HttpMethod getMethod() {
		return method;
	}

	public URI getURI() {
		return uri;
	}

	public HttpParams getParams() {
		return params;
	}

	public void setParams(HttpParams params) {
		this.params = params;
	}

	public HttpEntity getEntity() {
		return entity;
	}

	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	@Override
	public HttpHeaders getHeaders() {
		return headers;
	}
}
