package com.foxinmy.weixin4j.type.mch;

/**
 * 对账单类型
 * 
 * @className BillType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年10月31日
 * @since JDK 1.6
 * @see
 */
public enum BillType {
	/**
	 * 全部
	 */
	ALL(0),
	/**
	 * 成功订单
	 */
	SUCCESS(1),
	/**
	 * 退款订单
	 */
	REFUND(2);
	private int val;

	BillType(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
