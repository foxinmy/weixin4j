package com.foxinmy.weixin4j.exception;

import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 调用微信接口抛出的异常
 * 
 * @className WeixinException
 * @author jy.hu
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

	public WeixinException(Throwable e) {
		super(e);
	}

	public WeixinException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public String getMessage() {
		StringBuilder buf = new StringBuilder();
		if (StringUtil.isNotBlank(errorCode)) {
			buf.append(errorCode);
		}
		if (StringUtil.isNotBlank(errorMsg)) {
			buf.append(" ").append(errorMsg);
		}
		if (buf.length() == 0) {
			return super.getMessage();
		}
		return buf.toString();
	}
}
