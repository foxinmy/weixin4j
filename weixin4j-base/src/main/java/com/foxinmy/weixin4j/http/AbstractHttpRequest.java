package com.foxinmy.weixin4j.http;

import java.util.ArrayList;
import java.util.List;

/**
 * reference of apache pivot
 * 
 * @className AbstractHttpRequest
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public abstract class AbstractHttpRequest extends AbstractHttpMessage implements
		HttpRequest {

	@Override
	public HttpVersion getProtocolVersion() {
		return null;
	}

	@Override
	public HttpParams getParams() {
		return new HttpParams();
	}

	@Override
	public void addHeader(Header header) {
		super.headers.add(header);
	}

	@Override
	public boolean removeHeader(String name) {
		List<Header> headersFound = new ArrayList<Header>();
		for (int i = 0; i < headers.size(); i++) {
			Header header = headers.get(i);
			if (header.getName().equalsIgnoreCase(name)) {
				headersFound.add(header);
			}
		}
		return super.headers.removeAll(headersFound);
	}

	@Override
	public boolean removeHeader(Header header) {
		return super.headers.remove(header);
	}
}
