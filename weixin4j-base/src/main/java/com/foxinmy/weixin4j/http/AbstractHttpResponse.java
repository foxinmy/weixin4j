package com.foxinmy.weixin4j.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 
 * @className AbstractHttpResponse
 * @author jy
 * @date 2015年9月7日
 * @since JDK 1.6
 * @see
 */
public abstract class AbstractHttpResponse implements HttpResponse {

	private final byte[] content;

	public AbstractHttpResponse(byte[] content) {
		this.content = content;
	}

	@Override
	public byte[] getContent() {
		return content;
	}

	@Override
	public InputStream getBody() {
		return content != null ? new ByteArrayInputStream(content) : null;
	}
}
