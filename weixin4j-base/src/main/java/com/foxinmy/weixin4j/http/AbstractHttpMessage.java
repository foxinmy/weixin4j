package com.foxinmy.weixin4j.http;

import java.util.ArrayList;
import java.util.List;

/**
 * reference of apache pivot
 * 
 * @className AbstractHttpMessage
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public abstract class AbstractHttpMessage implements HttpMessage {

	protected final List<Header> headers;

	public AbstractHttpMessage() {
		this.headers = new ArrayList<Header>(16);
	}

	@Override
	public Header[] getAllHeaders() {
		return headers.toArray(new Header[headers.size()]);
	}

	@Override
	public boolean containsHeader(String name) {
		for (int i = 0; i < headers.size(); i++) {
			Header header = headers.get(i);
			if (name.equalsIgnoreCase(header.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setHeaders(Header... headers) {
		this.headers.clear();
		if (headers == null) {
			return;
		}
		for (int i = 0; i < headers.length; i++) {
			this.headers.add(headers[i]);
		}
	}

	@Override
	public Header[] getHeaders(String name) {
		List<Header> headersFound = new ArrayList<Header>();
		for (int i = 0; i < headers.size(); i++) {
			Header header = headers.get(i);
			if (name.equalsIgnoreCase(header.getName())) {
				headersFound.add(header);
			}
		}
		return headersFound.toArray(new Header[headersFound.size()]);
	}

	@Override
	public Header getFirstHeader(String name) {
		for (int i = 0; i < headers.size(); i++) {
			Header header = headers.get(i);
			if (name.equalsIgnoreCase(header.getName())) {
				return header;
			}
		}
		return null;
	}

	@Override
	public Header getLastHeader(String name) {
		// start at the end of the list and work backwards
		for (int i = headers.size() - 1; i >= 0; i--) {
			Header header = headers.get(i);
			if (name.equalsIgnoreCase(header.getName())) {
				return header;
			}
		}
		return null;
	}
}
