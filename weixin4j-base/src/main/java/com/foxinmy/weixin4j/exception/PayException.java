package com.foxinmy.weixin4j.exception;

/**
 * 调用微信支付抛出的异常
 * 
 * @className PayException
 * @author jy
 * @date 2014年10月28日
 * @since JDK 1.7
 * @see
 */
public class PayException extends WeixinException {
	private static final long serialVersionUID = 7148145661883468514L;

	public PayException(String errorMsg) {
		super(errorMsg);
	}

	public PayException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}
}
