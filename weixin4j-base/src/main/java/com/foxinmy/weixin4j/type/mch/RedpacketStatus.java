package com.foxinmy.weixin4j.type.mch;

/**
 * 红包状态
 * @className RedpacketStatus
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月4日
 * @since JDK 1.6
 * @see
 */
public enum RedpacketStatus {
	/**
	 * 发放中
	 */
	SENDING,
	/**
	 * 已发放待领取
	 */
	SENT,
	/**
	 * 发放失败
	 */
	FAILED,
	/**
	 * 已领取
	 */
	RECEIVED,
	/**
	 * 已退款
	 */
	REFUND;
}
