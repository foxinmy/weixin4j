package com.foxinmy.weixin4j.type.mch;

/**
 * 退款渠道
 * 
 * @className RefundChannel
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月6日
 * @since JDK 1.6
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
	BANK,
	/**
	 * 原账户异常退到其他余额账户
	 */
	OTHER_BALANCE,
	/**
	 * 原银行卡异常退到其他银行卡
	 */
	OTHER_BANKCARD
}
