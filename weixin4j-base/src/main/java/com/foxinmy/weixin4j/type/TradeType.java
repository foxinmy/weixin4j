package com.foxinmy.weixin4j.type;

/**
 * 微信支付类型
 * 
 * @className TradeType
 * @author jy
 * @date 2014年10月21日
 * @since JDK 1.7
 * @see
 */
public enum TradeType {
	/**
	 * H5页面上的JSAPI支付
	 */
	JSAPI,
	/**
	 * 刷卡支付
	 */
	MICROPAY,
	/**
	 * 扫描支付
	 */
	NATIVE,
	/**
	 * APP支付
	 */
	APP;
}
