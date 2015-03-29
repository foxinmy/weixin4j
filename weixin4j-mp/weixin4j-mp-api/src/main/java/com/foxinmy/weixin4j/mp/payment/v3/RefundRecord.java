package com.foxinmy.weixin4j.mp.payment.v3;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.mp.type.CurrencyType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * V3退款记录
 * 
 * @className RefundRecord
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.payment.v3.RefundDetail
 */
@XStreamAlias("xml")
public class RefundRecord extends ApiResult {

	private static final long serialVersionUID = -2971132874939642721L;

	/**
	 * 微信订单号
	 */
	@XStreamAlias("transaction_id")
	@JSONField(name = "transaction_id")
	private String transactionId;
	/**
	 * 商户订单号
	 */
	@XStreamAlias("out_trade_no")
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	/**
	 * 订单总金额
	 */
	@XStreamAlias("total_fee")
	@JSONField(name = "total_fee")
	private int totalFee;
	/**
	 * 订单金额货币种类
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XStreamAlias("fee_type")
	@JSONField(name = "fee_type")
	private CurrencyType feeType;
	/**
	 * 现金支付金额
	 */
	@XStreamAlias("cash_fee")
	@JSONField(name = "cash_fee")
	private int cashFee;
	/**
	 * 现金支付金额货币种类
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XStreamAlias("cash_fee_type")
	@JSONField(name = "cash_fee_type")
	private CurrencyType cashFeeType;
	/**
	 * 退款总金额
	 */
	@XStreamAlias("refund_fee")
	@JSONField(name = "refund_fee")
	private int refundFee;
	/**
	 * 代金券或立减优惠退款金额=订单金额-现金退款金额，注意：满立减金额不会退回
	 */
	@XStreamAlias("coupon_refund_fee")
	@JSONField(name = "coupon_refund_fee")
	private Integer couponRefundFee;
	/**
	 * 退款笔数
	 */
	@XStreamAlias("refund_count")
	@JSONField(name = "refund_count")
	private int count;
	/**
	 * 退款详情
	 */
	@XStreamOmitField
	@JSONField(serialize = false, deserialize = false)
	private List<RefundDetail> details;

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatCashFee() {
		return cashFee / 100d;
	}

	public int getCashFee() {
		return cashFee;
	}

	public CurrencyType getFeeType() {
		return feeType;
	}

	public CurrencyType getCashFeeType() {
		return cashFeeType;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatCouponRefundFee() {
		return couponRefundFee != null ? couponRefundFee.intValue() / 100d : 0d;
	}

	public Integer getCouponRefundFee() {
		return couponRefundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatTotalFee() {
		return totalFee / 100d;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public int getCount() {
		return count;
	}

	public List<RefundDetail> getDetails() {
		return details;
	}

	public void setDetails(List<RefundDetail> details) {
		this.details = details;
	}

	public int getRefundFee() {
		return refundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false, deserialize = false)
	public double getFormatRefundFee() {
		return refundFee / 100d;
	}

	@Override
	public String toString() {
		return "RefundRecord [transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", totalFee=" + getFormatTotalFee()
				+ ", feeType=" + feeType + ", cashFee=" + getFormatCashFee()
				+ ", cashFeeType=" + cashFeeType + ", refundFee="
				+ getFormatRefundFee() + ", couponRefundFee="
				+ getFormatCouponRefundFee() + ", count=" + count
				+ ", details=" + details + ", " + super.toString() + "]";
	}
}
