package com.foxinmy.weixin4j.wxa.api;

import java.io.Serializable;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;

class WxaApiResult implements Serializable {

	private static final long serialVersionUID = 2018052601L;

	public static final TypeReference<WxaApiResult> TYPE_REFERENCE
		= new TypeReference<WxaApiResult>() {
		};

	private int errCode;
	private String errMsg;

	public WxaApiResult() {
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public WeixinException toWeixinException() {
		return new WeixinException(Integer.toString(errCode), errMsg);
	}

	public void checkErrCode() throws WeixinException {
		if (getErrCode() != 0) {
			throw this.toWeixinException();
		}
	}

}
