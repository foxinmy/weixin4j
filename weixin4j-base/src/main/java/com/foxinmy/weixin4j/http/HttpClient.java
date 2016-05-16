package com.foxinmy.weixin4j.http;

import java.util.Set;

import com.foxinmy.weixin4j.http.entity.HttpEntity;

/**
 * HTTP 接口
 * 
 * @className HttpClient
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月30日
 * @since JDK 1.6
 * @see
 */
public interface HttpClient {

	// get
	HttpResponse get(String url) throws HttpClientException;

	HttpResponse get(String url, URLParameter... parameters)
			throws HttpClientException;

	// head
	HttpHeaders head(String url) throws HttpClientException;

	HttpHeaders head(String url, URLParameter... parameters)
			throws HttpClientException;

	// post
	HttpResponse post(String url) throws HttpClientException;

	HttpResponse post(String url, URLParameter... parameters)
			throws HttpClientException;

	HttpResponse post(String url, HttpEntity httpEntity)
			throws HttpClientException;

	// put
	void put(String url) throws HttpClientException;

	void put(String url, URLParameter... parameters) throws HttpClientException;

	// delete
	void delete(String url) throws HttpClientException;

	void delete(String url, URLParameter... parameters)
			throws HttpClientException;

	// OPTIONS
	Set<HttpMethod> options(String url) throws HttpClientException;

	Set<HttpMethod> options(String url, URLParameter... parameters)
			throws HttpClientException;

	/**
	 * 处理请求
	 * 
	 * @param request
	 *            请求对象
	 * @return 响应对象
	 * @throws HttpClientException
	 */
	HttpResponse execute(HttpRequest request) throws HttpClientException;
}
