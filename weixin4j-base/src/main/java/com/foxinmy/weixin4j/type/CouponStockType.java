package com.foxinmy.weixin4j.type;

/**
 * 代金券批次类型
 * 
 * @className CouponStockType
 * @author jy
 * @date 2015年3月27日
 * @since JDK 1.7
 * @see
 */
public enum CouponStockType {
	/**
	 * 批量型
	 */
	BATCH(1),
	/**
	 * 触发型
	 */
	TRIGGER(2);
	private int val;

	CouponStockType(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
