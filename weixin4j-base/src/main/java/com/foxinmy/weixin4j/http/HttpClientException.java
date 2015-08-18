package com.foxinmy.weixin4j.http;


public class HttpClientException extends Exception {

	private static final long serialVersionUID = -1760373205801759702L;

	/**
	 * Construct a new instance of {@code HttpClientException} with the given
	 * message.
	 * 
	 * @param msg
	 *            the message
	 */
	public HttpClientException(String msg) {
		super(msg);
	}

	/**
	 * Construct a new instance of {@code HttpClientException} with the given
	 * message and exception.
	 * 
	 * @param msg
	 *            the message
	 * @param ex
	 *            the exception
	 */
	public HttpClientException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
