package com.foxinmy.weixin4j.type.mch;

/**
 * 代金券状态
 * 
 * @className CouponStatus
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月27日
 * @since JDK 1.6
 * @see
 */
public enum CouponStatus {
	/**
	 * 已激活
	 */
	ACTIVATED(2),
	/**
	 * 已锁定
	 */
	LOCKED(4),
	/**
	 * 已实扣
	 */
	USED(8);
	private int val;

	CouponStatus(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
