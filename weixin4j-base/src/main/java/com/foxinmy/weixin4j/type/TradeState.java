package com.foxinmy.weixin4j.type;

/**
 * 交易状态
 * 
 * @className TradeState
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月2日
 * @since JDK 1.6
 * @see
 */
public enum TradeState {
	/**
	 * 支付成功
	 */
	SUCCESS,
	/**
	 * 转入退款
	 */
	REFUND,
	/**
	 * 未支付
	 */
	NOTPAY,
	/**
	 * 已关闭
	 */
	CLOSED,
	/**
	 * 已撤销
	 */
	REVOKED,
	/**
	 * 用户支付中
	 */
	USERPAYING,
	/**
	 * 未支付(输入密码或 确认支付超时)
	 */
	NOPAY,
	/**
	 * 支付失败(其他 原因,如银行返回失败)
	 */
	PAYERROR;
}
