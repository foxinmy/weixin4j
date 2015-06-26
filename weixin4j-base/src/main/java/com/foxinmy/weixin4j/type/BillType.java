package com.foxinmy.weixin4j.type;

/**
 * 对账单类型
 * 
 * @className BillType
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.7
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
