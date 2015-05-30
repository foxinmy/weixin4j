package com.foxinmy.weixin4j.http.apache;

public enum HttpMultipartMode {

	/** RFC 822, RFC 2045, RFC 2046 compliant */
	STRICT,
	/**
	 * browser-compatible mode, i.e. only write Content-Disposition; use content
	 * charset
	 */
	BROWSER_COMPATIBLE
}
