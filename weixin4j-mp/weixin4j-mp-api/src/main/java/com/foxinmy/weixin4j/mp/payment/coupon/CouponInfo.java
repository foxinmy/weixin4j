package com.foxinmy.weixin4j.mp.payment.coupon;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 代金券信息
 * 
 * @className CouponBase
 * @author jy
 * @date 2015年3月24日
 * @since JDK 1.7
 * @see
 */
public class CouponInfo implements Serializable {

	private static final long serialVersionUID = -8744999305258786901L;

	// 代金券或立减优惠批次ID
	@XStreamAlias("coupon_batch_id")
	@JSONField(name = "coupon_batch_id")
	private String couponBatchId;
	// 代金券或立减优惠ID
	@XStreamAlias("coupon_id")
	@JSONField(name = "coupon_id")
	private String couponId;
	// 单个代金券或立减优惠支付金额
	@XStreamAlias("coupon_fee")
	@JSONField(name = "coupon_fee")
	private Integer couponFee;

	public String getCouponBatchId() {
		return couponBatchId;
	}

	public String getCouponId() {
		return couponId;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatCouponFee() {
		return couponFee / 100d;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	@Override
	public String toString() {
		return "couponBatchId=" + couponBatchId + ", couponId=" + couponId
				+ ", couponFee=" + couponFee;
	}
}
