package com.foxinmy.weixin4j.mp.payment.v3;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.RefundChannel;
import com.foxinmy.weixin4j.mp.type.RefundStatus;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * V3退款详细
 * 
 * @className RefundDetail
 * @author jy
 * @date 2014年11月6日
 * @since JDK 1.7
 * @see
 */
public class RefundDetail extends ApiResult {

	private static final long serialVersionUID = -3687863914168618620L;

	@XStreamAlias("out_refund_no")
	@JSONField(name = "out_refund_no")
	private String outRefundNo; // 商户退款单号
	@XStreamAlias("refund_id")
	@JSONField(name = "refund_id")
	private String refundId; // 微信退款单号
	@XStreamAlias("refund_channel")
	@JSONField(name = "refund_channel")
	private String refundChannel;// 退款渠道:ORIGINAL—原路退款,默认 BALANCE—退回到余额
	@XStreamAlias("refund_fee")
	@JSONField(name = "refund_fee")
	private int refundFee; // 退款总金额,单位为分,可以做部分退款
	@XStreamAlias("refund_status")
	@JSONField(name = "refund_status")
	private String refundStatus; // 退款状态
	@XStreamAlias("coupon_refund_fee")
	@JSONField(name = "coupon_refund_fee")
	private int couponRefundFee; // 现金券退款金额<=退款金额,退款金额-现金券退款金额为现金

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	@JSONField(deserialize = false, serialize = false)
	public RefundChannel getFormatRefundChannel() {
		if (StringUtils.isNotBlank(refundChannel)) {
			return RefundChannel.valueOf(refundChannel.toUpperCase());
		}
		return null;
	}

	public int getRefundFee() {
		return refundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(deserialize = false, serialize = false)
	public double getFormatRefundFee() {
		return refundFee / 100d;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	@JSONField(deserialize = false, serialize = false)
	public RefundStatus getFormatRefundStatus() {
		if (StringUtils.isNotBlank(refundStatus)) {
			return RefundStatus.valueOf(refundStatus);
		}
		return null;
	}

	public int getCouponRefundFee() {
		return couponRefundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(deserialize = false, serialize = false)
	public double getFormatCouponRefundFee() {
		return couponRefundFee / 100d;
	}

	@Override
	public String toString() {
		return "RefundDetail [outRefundNo=" + outRefundNo + ", refundId="
				+ refundId + ", refundChannel=" + refundChannel
				+ ", refundFee=" + refundFee + ", refundStatus=" + refundStatus
				+ ", couponRefundFee=" + couponRefundFee
				+ ", getFormatRefundChannel()=" + getFormatRefundChannel()
				+ ", getFormatRefundStatus()=" + getFormatRefundStatus()
				+ ", getFormatCouponRefundFee()=" + getFormatCouponRefundFee();
	}
}
