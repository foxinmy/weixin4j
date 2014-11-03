package com.foxinmy.weixin4j.mp.payment.v3;

import java.io.Serializable;

import com.foxinmy.weixin4j.mp.payment.RefundStatus;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 退款详细
 * 
 * @className RefundDetail
 * @author jy
 * @date 2014年11月2日
 * @since JDK 1.7
 * @see
 */
public class RefundDetail implements Serializable {

	private static final long serialVersionUID = 2828640496307351988L;

	@XStreamAlias("out_refund_no")
	private String outRefundNo; // 商户退款单号
	@XStreamAlias("refund_id")
	private String refundId; // 微信退款单号
	@XStreamAlias("refund_channel")
	private String refundChannel; // 退款渠道 ORIGINAL—原路退款 BALANCE—退回到余额
	@XStreamAlias("refund_fee")
	private int refundFee; // 退款总金额,单位为分,可以做部分退款
	@XStreamAlias("coupon_refund_fee")
	private int couponRefundFee; // 现金券退款金额<=退款金额,退款金额-现金券退款金额为现金
	@XStreamAlias("refund_status")
	private RefundStatus refundStatus; // 退款状态

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public int getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}

	public int getCouponRefundFee() {
		return couponRefundFee;
	}

	public void setCouponRefundFee(int couponRefundFee) {
		this.couponRefundFee = couponRefundFee;
	}

	public RefundStatus getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(RefundStatus refundStatus) {
		this.refundStatus = refundStatus;
	}

	@Override
	public String toString() {
		return "RefundDetail [outRefundNo=" + outRefundNo + ", refundId="
				+ refundId + ", refundChannel=" + refundChannel
				+ ", refundFee=" + refundFee + ", couponRefundFee="
				+ couponRefundFee + ", refundStatus=" + refundStatus + "]";
	}
}
