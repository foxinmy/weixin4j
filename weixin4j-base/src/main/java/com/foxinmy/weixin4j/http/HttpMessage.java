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
	 * HTTP报头
	 * 
	 * @return
	 */
	HttpHeaders getHeaders();
}
