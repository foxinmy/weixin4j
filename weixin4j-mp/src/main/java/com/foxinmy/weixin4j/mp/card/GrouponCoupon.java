package com.foxinmy.weixin4j.mp.card;

import com.foxinmy.weixin4j.mp.type.CardType;

public class GrouponCoupon implements CardCoupon {

	@Override
	public CardType getCardType() {
		return CardType.GROUPON;
	}

	@Override
	public CouponBaseInfo getBaseInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CouponAdvancedInfo getAdvancedInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CouponDetailInfo getDetailInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
