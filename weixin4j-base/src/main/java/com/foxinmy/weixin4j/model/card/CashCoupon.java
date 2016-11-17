package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.CardType;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 代金券
 * 
 * @className CashCoupon
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月4日
 * @since JDK 1.6
 */
public class CashCoupon extends CardCoupon {
	/**
	 * 起用金额（单位为分）,如果无起用门槛则填0
	 */
	@JSONField(name = "least_cost")
	private int leastCost;
	/**
	 * 减免金额（单位为分）
	 */
	@JSONField(name = "reduce_cost")
	private final int reduceCost;

	/**
	 * 构造代金券
	 * 
	 * @param couponBaseInfo
	 *            基础信息
	 * @param reduceCost
	 *            减免金额 单位元
	 */
	public CashCoupon(CouponBaseInfo couponBaseInfo, double reduceCost) {
		super(couponBaseInfo);
		this.reduceCost = DateUtil.formatYuan2Fen(reduceCost);
	}

	public int getLeastCost() {
		return leastCost;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatLeastCost() {
		return leastCost / 100d;
	}

	public void setLeastCost(double leastCost) {
		this.leastCost = DateUtil.formatYuan2Fen(reduceCost);
	}

	public int getReduceCost() {
		return reduceCost;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatReduceCost() {
		return reduceCost / 100d;
	}

	@JSONField(serialize = false)
	@Override
	public CardType getCardType() {
		return CardType.CASH;
	}

	@Override
	public String toString() {
		return "CashCoupon [leastCost=" + leastCost + ", reduceCost="
				+ reduceCost + ", " + super.toString() + "]";
	}
}
