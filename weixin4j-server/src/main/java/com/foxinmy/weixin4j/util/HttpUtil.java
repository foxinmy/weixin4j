package com.foxinmy.weixin4j.util;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE;
import static io.netty.handler.codec.http.HttpHeaders.Names.USER_AGENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Date;

import com.foxinmy.weixin4j.startup.WeixinServerBootstrap;

/**
 * HTTP工具类
 * 
 * @className HttpUtil
 * @author jy
 * @date 2014年11月15日
 * @since JDK 1.7
 * @see
 */
public class HttpUtil {

	private static String SERVER = "netty4";
	private static String WEIXIN4J = "weixin4j-server";

	/**
	 * 创建只有状态的HttpResponse响应
	 * 
	 * @param status
	 *            响应状态
	 * @return HttpResponse
	 */
	public static HttpResponse createHttpResponse(HttpResponseStatus status) {
		FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1,
				status);
		createHeaders(httpResponse);
		return httpResponse;
	}

	/**
	 * 创建有内容的HttpResponse响应
	 * 
	 * @param content
	 *            响应内容
	 * @param status
	 *            响应状态
	 * @param contentType
	 *            响应类型
	 * @return HttpResponse
	 */
	public static HttpResponse createHttpResponse(String content,
			HttpResponseStatus status, String contentType) {
		FullHttpResponse httpResponse = null;

		httpResponse = new DefaultFullHttpResponse(HTTP_1_1, status,
				Unpooled.copiedBuffer(content, Consts.UTF_8));
		httpResponse.headers().set(
				CONTENT_TYPE,
				String.format("%s;encoding=%s", contentType,
						Consts.UTF_8.displayName()));
		httpResponse.headers().set(CONTENT_LENGTH,
				content.getBytes(Consts.UTF_8).length);
		createHeaders(httpResponse);
		return httpResponse;
	}

	private static void createHeaders(FullHttpResponse httpResponse) {
		httpResponse.headers().set(CONNECTION, Values.KEEP_ALIVE);
		httpResponse.headers().set(DATE, new Date());
		httpResponse.headers().set(HttpHeaders.Names.SERVER, SERVER);
		httpResponse.headers()
				.set(USER_AGENT,
						String.format("%s/%s", WEIXIN4J,
								WeixinServerBootstrap.VERSION));
	}
}
