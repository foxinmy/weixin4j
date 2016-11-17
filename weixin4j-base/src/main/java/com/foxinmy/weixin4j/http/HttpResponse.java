package com.foxinmy.weixin4j.http;

import java.io.InputStream;

/**
 * HTTP 响应
 * 
 * @className HttpResponse
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月30日
 * @since JDK 1.6
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
	HttpStatus getStatus();
	/**
	 * 响应内容
	 * 
	 * @return
	 */
	InputStream getBody();

	/**
	 * 响应内容
	 * 
	 * @return
	 */
	byte[] getContent();

	/**
	 * 释放资源
	 */
	void close();
}
