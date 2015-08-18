package com.foxinmy.weixin4j.http;

import java.io.InputStream;

/**
 * HTTP 响应
 * 
 * @className HttpResponse
 * @author jy
 * @date 2015年5月30日
 * @since JDK 1.7
 * @see
 */
public interface HttpResponse extends HttpMessage {
	/**
	 * HTTP协议
	 * 
	 * @return
	 */
	HttpVersion getProtocol();

	/**
	 * 响应状态
	 * 
	 * @return
	 */
	HttpStatus getStatus() throws HttpClientException;

	/**
	 * 响应内容
	 * 
	 * @return
	 */
	InputStream getBody() throws HttpClientException;

	/**
	 * 释放资源
	 */
	void close();
}
