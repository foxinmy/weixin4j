package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;

/**
 * 团购券
 * 
 * @className GrouponCoupon
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月4日
 * @since JDK 1.6
 */
public class GrouponCoupon extends CardCoupon {
	/**
	 * 团购详情
	 */
	@JSONField(name = "deal_detail")
	private final String explain;

	/**
	 * 构造团购券
	 * 
	 * @param couponBaseInfo
	 *            基础信息
	 * @param explain
	 *            团购详情 如：双人套餐\n -进口红酒一支。\n孜然牛肉一份。
	 */
	public GrouponCoupon(CouponBaseInfo couponBaseInfo, String explain) {
		super(couponBaseInfo);
		this.explain = explain;
	}

	public String getExplain() {
		return explain;
	}

	@JSONField(serialize = false)
	@Override
	public CardType getCardType() {
		return CardType.GROUPON;
	}

	@Override
	public String toString() {
		return "GrouponCoupon [explain=" + explain + ", " + super.toString()
				+ "]";
	}
}
