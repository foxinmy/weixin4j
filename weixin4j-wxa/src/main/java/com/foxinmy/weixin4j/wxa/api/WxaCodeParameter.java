package com.foxinmy.weixin4j.wxa.api;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

class WxaCodeParameter implements Serializable {

	private static final long serialVersionUID = 2018052201L;

	private String path;
	private Integer width;
	private Boolean autoColor;
	private Color color;
	private Boolean hyaline;

	public WxaCodeParameter(
		String path,
		Integer width,
		Boolean autoColor,
		java.awt.Color color,
		Boolean hyaline
	) {
		this.path = path;
		this.width = width;
		this.autoColor = autoColor;
		if (color != null) {
			this.color = new Color(color);
		}
		this.hyaline = hyaline;
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

	@JSONField(name = "auto_color")
	public Boolean getAutoColor() {
		return autoColor;
	}

	public void setAutoColor(Boolean autoColor) {
		this.autoColor = autoColor;
	}

	@JSONField(name = "line_color")
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@JSONField(name = "is_hyaline")
	public Boolean getHyaline() {
		return hyaline;
	}

	public void setHyaline(Boolean hyaline) {
		this.hyaline = hyaline;
	}

}
