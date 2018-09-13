package com.foxinmy.weixin4j.wxa.api;

import java.io.Serializable;

class Color implements Serializable {

	private static final long serialVersionUID = 2018052201L;

	private String r;
	private String g;
	private String b;

	public Color() {
	}

	public Color(java.awt.Color color) {
		this.r = String.valueOf(color.getRed());
		this.g = String.valueOf(color.getGreen());
		this.b = String.valueOf(color.getBlue());
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

}
