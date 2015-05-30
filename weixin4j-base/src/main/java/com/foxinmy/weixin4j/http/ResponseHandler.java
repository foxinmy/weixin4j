package com.foxinmy.weixin4j.http;

import java.io.IOException;

/**
 * 响应处理
 * 
 * @className ResponseHandler
 * @author jy
 * @date 2015年5月30日
 * @since JDK 1.7
 * @see
 */
public interface ResponseHandler<T> {
	T handleResponse(HttpResponse response) throws IOException;
}
