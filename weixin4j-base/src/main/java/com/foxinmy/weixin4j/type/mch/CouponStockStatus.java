package com.foxinmy.weixin4j.type.mch;

/**
 * 代金券批次状态
 * 
 * @className CouponStockStatus
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月27日
 * @since JDK 1.6
 * @see
 */
public enum CouponStockStatus {
	/**
	 * 未激活
	 */
	INACTIVE(1),
	/**
	 * 审批中
	 */
	APPROVAL_PROCESS(2),
	/**
	 * 已激活
	 */
	ACTIVATED(4),
	/**
	 * 已作废
	 */
	SUPERSEDED(8),
	/**
	 * 中止发放
	 */
	SUSPEND(16);
	private int val;

	CouponStockStatus(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}
}
