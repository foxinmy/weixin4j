package com.foxinmy.weixin4j.exception;

import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.WeixinErrorUtil;

/**
 * 调用微信接口抛出的异常
 *
 * @className WeixinException
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 * @see
 */
public class WeixinException extends Exception {

	private static final long serialVersionUID = 7148145661883468514L;

	private String code;
	private String desc;

	public WeixinException(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public WeixinException(String desc) {
		this.code = "-1";
		this.desc = desc;
	}

	public WeixinException(Throwable e) {
		super(e);
	}

	public WeixinException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getErrorCode() {
		return code;
	}

	public String getErrorMsg() {
		return desc;
	}

	public String getErrorText() {
		return WeixinErrorUtil.getText(code);
	}

	@Override
	public String getMessage() {
		StringBuilder buf = new StringBuilder();
		if (StringUtil.isNotBlank(code)) {
			buf.append(code);
		}
		if (StringUtil.isNotBlank(desc)) {
			buf.append(" >> ").append(desc);
		}
		if (buf.length() > 0) {
			buf.append(" >> ").append(WeixinErrorUtil.getText(code));
			return buf.toString();
		} else {
			return super.getMessage();
		}
	}
}
