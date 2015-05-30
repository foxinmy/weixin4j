package com.foxinmy.weixin4j.http;

import java.net.URI;

/**
 * HTTP 请求
 * 
 * @className HttpRequest
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public interface HttpRequest extends HttpMessage {

	/**
	 * 请求方式
	 * 
	 * @return
	 */
	HttpMethod getMethod();

	/**
	 * 请求路径
	 * 
	 * @return
	 */
	URI getURI();

	/**
	 * 请求参数
	 * 
	 * @return
	 */
	HttpParams getParams();

	/**
	 * 新增表头
	 * 
	 * @param header
	 */
	void addHeader(Header header);

	/**
	 * 移除表头
	 * 
	 * @param name
	 * @return
	 */
	boolean removeHeader(String name);

	/**
	 * 移除表头
	 * 
	 * @param name
	 * @return
	 */
	boolean removeHeader(Header name);
}
