package com.foxinmy.weixin4j.exception;

/**
 * 调用微信接口抛出的异常
 * @className WeixinException
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 * @see
 */
public class WeixinException extends Exception {

	private static final long serialVersionUID = 7148145661883468514L;

	private int errorCode;
	private String errorMsg;

	public WeixinException(int errorCode) {
		this.errorCode = errorCode;
	}

	public WeixinException(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public WeixinException(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}
}
