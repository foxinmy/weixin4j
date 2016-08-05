package com.foxinmy.weixin4j.type.mch;

/**
 * 退款类型
 * 
 * @className RefundType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年12月31日
 * @since JDK 1.6
 * @see
 */
public enum RefundType {
	/**
	 * 1:商户号余额退款;
	 */
	BALANCE(1),
	/**
	 * 2:现金帐号 退款;
	 */
	CASH(2),
	/**
	 * 3:优先商户号退款,若商户号余额不足, 再做现金帐号退款。 使用 2 或 3 时,需联系财 付通开通此功能
	 */
	BOTH(3);

	private int val;

	RefundType(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
