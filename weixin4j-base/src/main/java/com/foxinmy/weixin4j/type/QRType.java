package com.foxinmy.weixin4j.type;

/**
 * 二维码类型
 * 
 * @className QRType
 * @author jy
 * @date 2014年11月4日
 * @since JDK 1.6
 * @see
 */
public enum QRType {
	/**
	 * 临时二维码
	 */
	QR_SCENE,
	/**
	 * 永久二维码(场景值为数字范围在1-100000之间)
	 */
	QR_LIMIT_SCENE,
	/**
	 * 永久二维码(场景值为字符串长度在1-64之间)
	 */
	QR_LIMIT_STR_SCENE;
}
