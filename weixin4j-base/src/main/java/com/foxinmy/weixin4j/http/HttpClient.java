package com.foxinmy.weixin4j.http;

import java.io.IOException;

/**
 * HTTP 接口
 * 
 * @className HttpClient
 * @author jy
 * @date 2015年5月30日
 * @since JDK 1.7
 * @see
 */
public interface HttpClient {

	/**
	 * 处理请求
	 * 
	 * @param request
	 *            请求
	 * @return 响应
	 * @throws IOException
	 */
	HttpResponse execute(HttpRequest request) throws IOException;

	/**
	 * 处理请求
	 * 
	 * @param request
	 *            请求
	 * @param handler
	 *            处理器
	 * @return 泛型处理结果
	 * @throws IOException
	 */
	<T> T execute(HttpRequest request, ResponseHandler<? extends T> handler)
			throws IOException;
}
