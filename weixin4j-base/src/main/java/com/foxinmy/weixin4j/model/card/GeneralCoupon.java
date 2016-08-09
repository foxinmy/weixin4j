package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;

/**
 * 普通优惠券
 * 
 * @className GeneralCoupon
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月4日
 * @since JDK 1.6
 */
public class GeneralCoupon extends CardCoupon {
	/**
	 * 优惠详情
	 */
	@JSONField(name = "default_detail")
	private final String explain;

	/**
	 * 构造普通优惠券
	 * 
	 * @param couponBaseInfo
	 *            基础信息
	 * @param explain
	 *            优惠详情 如：音乐木盒
	 */
	public GeneralCoupon(CouponBaseInfo couponBaseInfo, String explain) {
		super(couponBaseInfo);
		this.explain = explain;
	}

	public String getExplain() {
		return explain;
	}

	@JSONField(serialize = false)
	@Override
	public CardType getCardType() {
		return CardType.GENERAL_COUPON;
	}

	@Override
	public String toString() {
		return "GrouponCoupon [explain=" + explain + ", " + super.toString()
				+ "]";
	}
}
