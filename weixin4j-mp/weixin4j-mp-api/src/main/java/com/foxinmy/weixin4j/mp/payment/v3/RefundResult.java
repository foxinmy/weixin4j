package com.foxinmy.weixin4j.mp.payment.v3;

import org.apache.commons.lang3.StringUtils;

import com.foxinmy.weixin4j.mp.payment.ApiResult;
import com.foxinmy.weixin4j.mp.type.RefundChannel;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 退款申请结果
 * 
 * @className RefundResult
 * @author jy
 * @date 2014年11月6日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class RefundResult extends ApiResult {

	private static final long serialVersionUID = -3687863914168618620L;

	// 微信订单号
	@XStreamAlias("transaction_id")
	private String transactionId;
	// 商户系统内部的订单号
	@XStreamAlias("out_trade_no")
	private String outTradeNo;
	// 商户退款单号
	@XStreamAlias("out_refund_no")
	private String outRefundNo;
	// 微信退款单号
	@XStreamAlias("refund_id")
	private String refundId;
	// 退款渠道 ORIGINAL—原路退款,默认 BALANCE—退回到余额
	@XStreamAlias("refund_channel")
	private String refundChannel;
	// 退款总金额,单位为元,可以做部分退款
	@XStreamAlias("refund_fee")
	private int refundFee;
	// 现金券退款金额<=退款金 额,退款金额-现金券退款金 额为现金
	@XStreamAlias("coupon_refund_fee")
	private int couponRefundFee;

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public String getRefundChannel() {
		return StringUtils.isBlank(refundChannel) ? RefundChannel.BALANCE
				.name() : refundChannel;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getRefundFee() {
		return refundFee / 100d;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	public double getCouponRefundFee() {
		return couponRefundFee / 100d;
	}

	@Override
	public String toString() {
		return "RefundResult [transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", outRefundNo=" + outRefundNo + ", refundId="
				+ refundId + ", refundChannel=" + refundChannel
				+ ", refundFee=" + refundFee + ", couponRefundFee="
				+ couponRefundFee + ", getAppId()=" + getAppId()
				+ ", getMchId()=" + getMchId() + ", getNonceStr()="
				+ getNonceStr() + ", getSign()=" + getSign()
				+ ", getDeviceInfo()=" + getDeviceInfo() + ", getReturnCode()="
				+ getReturnCode() + ", getReturnMsg()=" + getReturnMsg()
				+ ", getResultCode()=" + getResultCode() + ", getErrCode()="
				+ getErrCode() + ", getErrCodeDes()=" + getErrCodeDes() + "]";
	}
}
