package com.foxinmy.weixin4j.type;

/**
 * 微信支付类型
 * 
 * @className TradeType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月21日
 * @since JDK 1.6
 * @see
 */
public enum TradeType {
	/**
	 * JS支付
	 */
	JSAPI(true),
	/**
	 * 刷卡支付:不需要设置TradeType参数
	 */
	MICROPAY(false),
	/**
	 * 扫码支付
	 */
	NATIVE(true),
	/**
	 * APP支付
	 */
	APP(true),
	/**
	 * WAP支付
	 */
	WAP(true);

	boolean isPayRequestParameter;

	private TradeType(boolean isPayRequestParameter) {
		this.isPayRequestParameter = isPayRequestParameter;
	}

	/**
	 * 是否作为支付请求参数
	 * 
	 * @return
	 */
	public boolean isPayRequestParameter() {
		return isPayRequestParameter;
	}
}
