package com.foxinmy.weixin4j.http;

import java.io.Serializable;

/**
 * 状态码
 * 
 * @className StatusLine
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public class StatusLine implements Serializable {

	private static final long serialVersionUID = -4182333834588893408L;

	private final int statusCode;
	private final String statusText;

	public StatusLine(int statusCode, String statusText) {
		this.statusCode = statusCode;
		this.statusText = statusText;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	@Override
	public String toString() {
		return statusCode + "," + statusText;
	}
}
