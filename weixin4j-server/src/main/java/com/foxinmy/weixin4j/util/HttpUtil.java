package com.foxinmy.weixin4j.util;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE;
import static io.netty.handler.codec.http.HttpHeaders.Names.USER_AGENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
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
 * @since JDK 1.6
 * @see
 */
public class HttpUtil {

	private static String SERVER = "netty4";
	private static String WEIXIN4J = "weixin4j-server";

	/**
	 * 创建有内容的HttpResponse响应
	 * 
	 * @param content
	 *            响应内容
	 * @param status
	 *            响应状态
	 * @param contentType
	 *            响应类型
	 * @param request
	 *            请求对象
	 * @return HttpResponse
	 */
	public static HttpResponse createHttpResponse(String content,
			String contentType) {
		FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1,
				HttpResponseStatus.OK, Unpooled.copiedBuffer(content,
						ServerToolkits.UTF_8));
		httpResponse.headers().set(
				CONTENT_TYPE,
				String.format("%s;encoding=%s", contentType,
						ServerToolkits.UTF_8.displayName()));
		httpResponse.headers().set(CONTENT_LENGTH,
				content.getBytes(ServerToolkits.UTF_8).length);
		resolveHeaders(httpResponse);
		return httpResponse;
	}

	public static void resolveHeaders(FullHttpResponse httpResponse) {
		/*if (HttpHeaders.isKeepAlive(httpRequest)) {
			httpResponse.headers().set(CONNECTION, Values.KEEP_ALIVE);
		}
		if (HttpHeaders.isTransferEncodingChunked(httpRequest)) {
			httpResponse.headers().set(TRANSFER_ENCODING, Values.CHUNKED);
		}*/
		httpResponse.headers().set(DATE, new Date());
		httpResponse.headers().set(SERVER, SERVER);
		httpResponse.headers()
				.set(USER_AGENT,
						String.format("%s/%s", WEIXIN4J,
								WeixinServerBootstrap.VERSION));
	}
}