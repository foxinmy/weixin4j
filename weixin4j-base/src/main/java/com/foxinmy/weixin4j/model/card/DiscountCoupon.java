package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;

/**
 * 折扣券
 * 
 * @className DiscountCoupon
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月4日
 * @since JDK 1.6
 */
public class DiscountCoupon extends CardCoupon {
	/**
	 * 打折额度百分比
	 */
	private final int discount;

	/**
	 * 构造折扣券
	 * 
	 * @param couponBaseInfo
	 *            基础信息
	 * @param discount
	 *            打折额度百分百，如：传入30就是七折。
	 */
	public DiscountCoupon(CouponBaseInfo couponBaseInfo, int discount) {
		super(couponBaseInfo);
		this.discount = discount;
	}

	public int getDiscount() {
		return discount;
	}

	@JSONField(serialize = false)
	@Override
	public CardType getCardType() {
		return CardType.DISCOUNT;
	}

	@Override
	public String toString() {
		return "DiscountCoupon [discount=" + discount + ", " + super.toString()
				+ "]";
	}
}
