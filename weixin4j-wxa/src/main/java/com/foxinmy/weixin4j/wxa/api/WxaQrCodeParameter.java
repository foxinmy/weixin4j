package com.foxinmy.weixin4j.wxa.api;

import java.io.Serializable;

class WxaQrCodeParameter implements Serializable {

	private static final long serialVersionUID = 2018052201L;

	private String path;
	private Integer width;

	public WxaQrCodeParameter(String path, Integer width) {
		this.path = path;
		this.width = width;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

}
