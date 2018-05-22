package com.foxinmy.weixin4j.wxa.api;

class WxaCodeError extends Exception {

	private static final long serialVersionUID = 2018052201L;

	private int errcode;
	private String errmsg;

	public WxaCodeError() {
	}

	public WxaCodeError(int errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	@Override
	public String getMessage() {
		return errmsg;
	}

}
