package com.foxinmy.weixin4j.util;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE;
import static io.netty.handler.codec.http.HttpHeaders.Names.SERVER;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.Date;

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

	public static HttpResponse createHttpResponse(String content,
			HttpResponseStatus status, String contentType) {
		if (content == null) {
			content = "";
		}
		if (StringUtil.isBlank(contentType)) {
			contentType = Consts.CONTENTTYPE$TEXT_PLAIN;
		}
		if (status == null) {
			status = OK;
		}
		FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1,
				status, Unpooled.copiedBuffer(content, Consts.UTF_8));
		httpResponse.headers().set(
				CONTENT_TYPE,
				String.format("%s;encoding=%s", contentType,
						Consts.UTF_8.displayName()));
		httpResponse.headers().set(CONTENT_LENGTH,
				content.getBytes(Consts.UTF_8).length);
		httpResponse.headers().set(CONNECTION, Values.KEEP_ALIVE);
		httpResponse.headers().set(DATE, new Date());
		httpResponse.headers().set(SERVER, "netty4");
		return httpResponse;
	}
}
