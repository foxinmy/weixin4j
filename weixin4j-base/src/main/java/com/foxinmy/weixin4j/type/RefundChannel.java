package com.foxinmy.weixin4j.type;

/**
 * 退款渠道
 * 
 * @className RefundChannel
 * @author jy
 * @date 2014年11月6日
 * @since JDK 1.7
 * @see
 */
public enum RefundChannel {
	/**
	 * 原路退款
	 */
	ORIGINAL,
	/**
	 * 退回到余额
	 */
	BALANCE,
	/**
	 * 财付通
	 */
	TENPAY,
	/**
	 * 银行
	 */
	BANK;
}
