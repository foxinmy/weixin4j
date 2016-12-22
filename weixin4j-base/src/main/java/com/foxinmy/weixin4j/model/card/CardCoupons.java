package com.foxinmy.weixin4j.model.card;

/**
 * 卡券构造器
 *
 * @className CardCoupons
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年6月1日
 * @since JDK 1.6
 * @see CashCoupon
 * @see DiscountCoupon
 * @see GeneralCoupon
 * @see GiftCoupon
 * @see GrouponCoupon
 */
public final class CardCoupons {
	private CardCoupons() {
	}

	/**
	 * 卡券基础信息构造器
	 * 
	 * @return
	 */
	public static CouponBaseInfo.Builder customBase() {
		return new CouponBaseInfo.Builder();
	}

	/**
	 * 卡券高级信息构造器
	 * 
	 * @return
	 */
	public static CouponAdvanceInfo.Builder customAdvance() {
		return new CouponAdvanceInfo.Builder();
	}

	public static MemberCard.Builder customMemberCard(){
		return  new MemberCard.Builder();
	}
	/**
	 * 创建代金券
	 * 
	 * @param builder
	 *            卡券基础信息构造器 必填
	 * @param reduceCost
	 *            减免金额 （单位为元） 必填
	 * @param leastCost
	 *            起用金额（单位为元）,如果无起用门槛则填0
	 */
	public static CardCoupon createCashCoupon(CouponBaseInfo.Builder builder,
			double reduceCost, double leastCost) {
		CouponBaseInfo couponBaseInfo = builder.build();
		CashCoupon coupon = new CashCoupon(couponBaseInfo, reduceCost);
		coupon.setLeastCost(leastCost);
		return coupon;
	}

	/**
	 * 创建折扣券
	 * 
	 * @param builder
	 *            卡券基础信息构造器 必填
	 * @param discount
	 *            打折额度百分百，如：传入30就是七折 必填
	 */
	public static CardCoupon createDiscountCoupon(
			CouponBaseInfo.Builder builder, int discount) {
		CouponBaseInfo couponBaseInfo = builder.build();
		DiscountCoupon coupon = new DiscountCoupon(couponBaseInfo, discount);
		return coupon;
	}

	/**
	 * 创建普通优惠券
	 * 
	 * @param builder
	 *            卡券基础信息构造器 必填
	 * @param explain
	 *            优惠详情 如：音乐木盒 必填
	 */
	public static CardCoupon createGeneralCoupon(
			CouponBaseInfo.Builder builder, String explain) {
		CouponBaseInfo couponBaseInfo = builder.build();
		GeneralCoupon coupon = new GeneralCoupon(couponBaseInfo, explain);
		return coupon;
	}

	/**
	 * 创建普通优惠券
	 * 
	 * @param builder
	 *            卡券基础信息构造器 必填
	 * @param explain
	 *            兑换说明 如：可兑换音乐木盒一个 必填
	 */
	public static CardCoupon createGiftCoupon(CouponBaseInfo.Builder builder,
			String explain) {
		CouponBaseInfo couponBaseInfo = builder.build();
		GiftCoupon coupon = new GiftCoupon(couponBaseInfo, explain);
		return coupon;
	}

	/**
	 * 创建团购券
	 * 
	 * @param builder
	 *            卡券基础信息构造器 必填
	 * @param explain
	 *            团购详情 如：双人套餐\n -进口红酒一支。\n孜然牛肉一份 必填
	 */
	public static CardCoupon createGrouponCoupon(
			CouponBaseInfo.Builder builder, String explain) {
		CouponBaseInfo couponBaseInfo = builder.build();
		GrouponCoupon coupon = new GrouponCoupon(couponBaseInfo, explain);
		return coupon;
	}


	public static MemberCard createMemberCard(CouponBaseInfo.Builder baseBuilder, MemberCard.Builder memberCardBudiler) {
		baseBuilder.build();
		MemberCard memberCard = new MemberCard(baseBuilder.build(), memberCardBudiler);
		return memberCard;
	}
}