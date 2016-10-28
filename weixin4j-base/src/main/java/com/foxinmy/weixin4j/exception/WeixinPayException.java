package com.foxinmy.weixin4j.exception;

/**
 * 调用微信支付抛出的异常
 * 
 * @className WeixinPayException
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月28日
 * @since JDK 1.6
 * @see
 */
public class WeixinPayException extends WeixinException {
	private static final long serialVersionUID = 7148145661883468514L;

	public WeixinPayException(String desc) {
		super(desc);
	}

	public WeixinPayException(String code, String desc) {
		super(code, desc);
	}

	public WeixinPayException(Throwable e) {
		super(e);
	}
}
