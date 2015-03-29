package com.foxinmy.weixin4j.mp.type;

/**
 * 二维码类型
 * 
 * @className QRType
 * @author jy
 * @date 2014年11月4日
 * @since JDK 1.7
 * @see
 */
public enum QRType {
	/**
	 * 临时二维码
	 */
	TEMPORARY("QR_SCENE"),
	/**
	 * 永久二维码
	 */
	PERMANENCE("QR_LIMIT_SCENE");
	private String name;

	QRType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
