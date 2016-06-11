package com.foxinmy.weixin4j.mp.card;

import com.foxinmy.weixin4j.mp.type.CardType;

/**
 * 卡券
 *
 * @className CardCoupon
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年4月4日
 * @since JDK 1.6
 * @see
 */
public interface CardCoupon {
	/**
	 * 卡券类型
	 *
	 * @return
	 */
	public CardType getCardType();

	/**
	 * 卡券基本信息
	 *
	 * @return
	 */
	public CouponBaseInfo getBaseInfo();

	/**
	 * 卡券高级信息
	 *
	 * @return
	 */
	public CouponAdvancedInfo getAdvancedInfo();

	/**
	 * 卡券详细信息
	 *
	 * @return
	 */
	public CouponDetailInfo getDetailInfo();
}
