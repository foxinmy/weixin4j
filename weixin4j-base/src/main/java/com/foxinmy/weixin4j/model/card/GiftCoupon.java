package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;

/**
 * 兑换券
 * 
 * @className GifCoupon
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月4日
 * @since JDK 1.6
 */
public class GiftCoupon extends CardCoupon {
	/**
	 * 兑换说明
	 */
	@JSONField(name = "gift")
	private final String explain;

	/**
	 * 构造兑换券
	 * 
	 * @param couponBaseInfo
	 *            基础信息
	 * @param explain
	 *            兑换说明 如：可兑换音乐木盒一个。
	 */
	public GiftCoupon(CouponBaseInfo couponBaseInfo, String explain) {
		super(couponBaseInfo);
		this.explain = explain;
	}

	public String getExplain() {
		return explain;
	}

	@JSONField(serialize = false)
	@Override
	public CardType getCardType() {
		return CardType.GIFT;
	}

	@Override
	public String toString() {
		return "GiftCoupon [explain=" + explain + ", " + super.toString() + "]";
	}
}
