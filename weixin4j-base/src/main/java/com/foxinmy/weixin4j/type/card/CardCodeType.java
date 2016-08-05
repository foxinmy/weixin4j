package com.foxinmy.weixin4j.type.card;

/**
 * 卡券码型
 * 
 * @className CardCodeType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月4日
 * @since JDK 1.7
 */
public enum CardCodeType {
	/**
	 * 文本
	 */
	CODE_TYPE_TEXT,
	/**
	 * 一维码
	 */
	CODE_TYPE_BARCODE,
	/**
	 * 二维码
	 */
	CODE_TYPE_QRCODE,
	/**
	 * 二维码无code显示
	 */
	CODE_TYPE_ONLY_QRCODE,
	/**
	 * 一维码无code显示
	 */
	CODE_TYPE_ONLY_BARCODE,
	/**
	 * 不显示code和条形码类型
	 */
	CODE_TYPE_NONE;
}
