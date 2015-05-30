package com.foxinmy.weixin4j.http;

/**
 * HTTP messages consist of requests from client to server and responses from
 * server to client.
 * 
 * @className HttpMessage
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public interface HttpMessage {

	/**
	 * HTTP版本
	 * 
	 * @return
	 */
	HttpVersion getProtocolVersion();

	/**
	 * 全部表头
	 * 
	 * @return
	 */
	Header[] getAllHeaders();

	/**
	 * 设置表头
	 * 
	 * @param headers
	 */
	void setHeaders(Header... headers);

	/**
	 * 是否包含某一表头
	 * 
	 * @param name
	 * @return
	 */
	boolean containsHeader(String name);

	/**
	 * 名称查找表头
	 * 
	 * @param name
	 * @return
	 */
	Header[] getHeaders(String name);

	/**
	 * 查找最先匹配表头
	 * 
	 * @param name
	 * @return
	 */
	Header getFirstHeader(String name);

	/**
	 * 查找最后匹配表头
	 * 
	 * @param name
	 * @return
	 */
	Header getLastHeader(String name);
}
