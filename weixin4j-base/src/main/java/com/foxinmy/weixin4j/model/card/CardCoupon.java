package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;

/**
 * 卡券
 *
 * @className CardCoupon
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年4月4日
 * @since JDK 1.6
 */
public abstract class CardCoupon {
	/**
	 * 卡券基础信息，必填属性
	 */
	@JSONField(name = "base_info")
	private final CouponBaseInfo couponBaseInfo;
	/**
	 * 卡券高级信息，选填属性
	 */
	@JSONField(name = "advanced_info")
	private CouponAdvanceInfo couponAdvanceInfo;

	/**
	 * 卡券
	 * 
	 * @param couponBaseInfo
	 *            基础信息
	 */
	protected CardCoupon(CouponBaseInfo couponBaseInfo) {
		this.couponBaseInfo = couponBaseInfo;
	}

	public  void cleanCantUpdateField(){
		this.couponBaseInfo.cleanCantUpdateField();
	}
	/**
	 * 卡券类型
	 *
	 * @return
	 */
	public abstract CardType getCardType();

	public CouponBaseInfo getCouponBaseInfo() {
		return couponBaseInfo;
	}

	public CouponAdvanceInfo getCouponAdvanceInfo() {
		return couponAdvanceInfo;
	}

	public void setCouponAdvanceInfo(CouponAdvanceInfo couponAdvanceInfo) {
		this.couponAdvanceInfo = couponAdvanceInfo;
	}

	@Override
	public String toString() {
		return "baseInfo=" + couponBaseInfo + ", advanceInfo="
				+ couponAdvanceInfo + ", cardType=" + getCardType();
	}
}
