package com.foxinmy.weixin4j.type.mch;

/**
 * 退款资金来源
 * @className RefundAccountType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年12月12日
 */
public enum RefundAccountType {
	/**
	 * ---未结算资金退款（默认使用未结算资金退款）
	 */
	REFUND_SOURCE_UNSETTLED_FUNDS,
	/**
	 * ---可用余额退款
	 */
	REFUND_SOURCE_RECHARGE_FUNDS
}
