package com.zone.weixin4j.exception;

/**
 * 微信异常
 * 
 * @className WeixinException
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 * @see
 */
public class WeixinException extends Exception {

	private static final long serialVersionUID = 7148145661883468514L;

	private String errorCode;
	private String errorMsg;

	public WeixinException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public WeixinException(String errorMsg) {
		this.errorCode = "-1";
		this.errorMsg = errorMsg;
	}

	public WeixinException(Exception e) {
		super(e);
	}

	public WeixinException(String errorMsg, Exception e) {
		super(e);
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public String getMessage() {
		return this.errorCode + "," + this.errorMsg;
	}
}
