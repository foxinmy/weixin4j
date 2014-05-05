package com.foxinmy.weixin4j.msg;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = 2539772564331987708L;

	private int code;
	private String msg;
	private String text;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ErrorMessage(int code, String msg, String text) {
		this.code = code;
		this.msg = msg;
		this.text = text;
	}
	public ErrorMessage() {

	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ErrorMessage code=").append(code);
		sb.append(", msg=").append(msg);
		sb.append(", text=").append(text).append("]");
		return sb.toString();
	}
	
}
