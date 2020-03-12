package com.foxinmy.weixin4j.type.mch;

/**
 * 代金券批次类型
 * 
 * @className CouponStockType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月27日
 * @since JDK 1.6
 * @see
 * @deprecated 迁移到子模块weixin4j-pay
 */
@Deprecated
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
