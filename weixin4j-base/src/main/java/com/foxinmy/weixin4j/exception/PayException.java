package com.foxinmy.weixin4j.exception;

public class PayException extends Exception {

	private static final long serialVersionUID = 7148145661883468514L;

	private String errorCode;
	private String errorMsg;

	public PayException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public PayException(String errorMsg) {
		this.errorCode = "-1";
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	@Override
	public String getMessage() {
		return String.format("%s,%s", getErrorMsg(), getErrorCode());
	}
}
