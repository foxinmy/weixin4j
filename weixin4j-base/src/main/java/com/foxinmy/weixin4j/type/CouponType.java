package com.foxinmy.weixin4j.type;

/**
 * 代金券类型
 * 
 * @className CouponType
 * @author jy
 * @date 2015年3月27日
 * @since JDK 1.6
 * @see
 */
public enum CouponType {
	/**
	 * 使用无门槛
	 */
	NO_THRESHOLD(1),
	/**
	 * 使用有门槛
	 */
	HAS_THRESHOLD(2),
	/**
	 * 门槛叠加
	 */
	THRESHOLD_PLUS(3);
	private int val;

	CouponType(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
