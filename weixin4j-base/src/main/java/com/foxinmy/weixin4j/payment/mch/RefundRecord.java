package com.foxinmy.weixin4j.payment.mch;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

/**
 * V3退款记录
 * 
 * @className RefundRecord
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.7
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundRecord extends ApiResult {

	private static final long serialVersionUID = -2971132874939642721L;

	/**
	 * 微信订单号
	 */
	@XmlElement(name = "transaction_id")
	@JSONField(name = "transaction_id")
	private String transactionId;
	/**
	 * 商户订单号
	 */
	@XmlElement(name = "out_trade_no")
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	/**
	 * 订单总金额
	 */
	@XmlElement(name = "total_fee")
	@JSONField(name = "total_fee")
	private int totalFee;
	/**
	 * 订单金额货币种类
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XmlElement(name = "fee_type")
	@JSONField(name = "fee_type")
	private String feeType;
	/**
	 * 现金支付金额
	 */
	@XmlElement(name = "cash_fee")
	@JSONField(name = "cash_fee")
	private int cashFee;
	/**
	 * 现金支付金额货币种类
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XmlElement(name = "cash_fee_type")
	@JSONField(name = "cash_fee_type")
	private String cashFeeType;
	/**
	 * 退款总金额
	 */
	@XmlElement(name = "refund_fee")
	@JSONField(name = "refund_fee")
	private int refundFee;
	/**
	 * 代金券或立减优惠退款金额=订单金额-现金退款金额，注意：满立减金额不会退回
	 */
	@XmlElement(name = "coupon_refund_fee")
	@JSONField(name = "coupon_refund_fee")
	private Integer couponRefundFee;
	/**
	 * 退款笔数
	 */
	@XmlElement(name = "refund_count")
	@JSONField(name = "refund_count")
	private int refundCount;
	/**
	 * 退款详情
	 * 
	 * @see com.foxinmy.weixin4j.payment.mch.RefundDetail
	 */
	@ListsuffixResult({ "^out_refund_no(_\\d)$", "^refund_.*(_\\d)$" })
	private List<RefundDetail> refundList;

	protected RefundRecord() {
		// jaxb required
	}

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
	@JSONField(serialize = false)
	public double getFormatCashFee() {
		return cashFee / 100d;
	}

	public int getCashFee() {
		return cashFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public String getCashFeeType() {
		return cashFeeType;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatFeeType() {
		return feeType != null ? CurrencyType.valueOf(feeType.toUpperCase())
				: null;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatCashFeeType() {
		return cashFeeType != null ? CurrencyType.valueOf(cashFeeType
				.toUpperCase()) : null;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
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
	@JSONField(serialize = false)
	public double getFormatTotalFee() {
		return totalFee / 100d;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public int getRefundCount() {
		return refundCount;
	}

	public List<RefundDetail> getRefundList() {
		return refundList;
	}

	public void setRefundList(List<RefundDetail> refundList) {
		this.refundList = refundList;
	}

	public int getRefundFee() {
		return refundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
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
				+ getFormatCouponRefundFee() + ", refundCount=" + refundCount
				+ ", refundList=" + refundList + ", " + super.toString() + "]";
	}
}
