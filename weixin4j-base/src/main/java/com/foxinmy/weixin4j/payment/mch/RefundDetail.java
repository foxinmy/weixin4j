package com.foxinmy.weixin4j.payment.mch;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.payment.coupon.CouponInfo;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.RefundChannel;
import com.foxinmy.weixin4j.type.RefundStatus;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

/**
 * V3退款详细
 * 
 * @className RefundDetail
 * @author jy
 * @date 2014年11月6日
 * @since JDK 1.7
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundDetail extends ApiResult {

	private static final long serialVersionUID = -3687863914168618620L;

	/**
	 * 商户退款单号
	 */
	@XmlElement(name = "out_refund_no")
	@JSONField(name = "out_refund_no")
	private String outRefundNo;
	/**
	 * 微信退款单号
	 */
	@XmlElement(name = "refund_id")
	@JSONField(name = "refund_id")
	private String refundId;
	/**
	 * 退款渠道:ORIGINAL—原路退款,默认 BALANCE—退回到余额
	 */
	@XmlElement(name = "refund_channel")
	@JSONField(name = "refund_channel")
	private String refundChannel;
	/**
	 * 退款总金额,单位为分,可以做部分退款
	 */
	@XmlElement(name = "refund_fee")
	@JSONField(name = "refund_fee")
	private int refundFee;
	/**
	 * 退款货币种类
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XmlElement(name = "refund_fee_type")
	@JSONField(name = "refund_fee_type")
	private String refundFeeType;
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
	 * 现金支付货币种类
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XmlElement(name = "cash_fee_type")
	@JSONField(name = "cash_fee_type")
	private String cashFeeType;
	/**
	 * 现金退款金额
	 */
	@XmlElement(name = "cash_refund_fee")
	@JSONField(name = "cash_refund_fee")
	private Integer cashRefundFee;
	/**
	 * 现金退款货币类型
	 * 
	 * @see com.foxinmy.weixin4j.mp.type.CurrencyType
	 */
	@XmlElement(name = "cash_refund_fee_type")
	@JSONField(name = "cash_refund_fee_type")
	private String cashRefundFeeType;
	/**
	 * 退款状态
	 */
	@XmlElement(name = "refund_status")
	@JSONField(name = "refund_status")
	private String refundStatus;
	/**
	 * 现金券退款金额<=退款金额,退款金额-现金券退款金额为现金
	 */
	@XmlElement(name = "coupon_refund_fee")
	@JSONField(name = "coupon_refund_fee")
	private Integer couponRefundFee;
	/**
	 * 代金券或立减优惠使用数量 <font
	 * color="red">微信支付文档上写的coupon_count,而实际测试拿到的是coupon_refund_count,做个记号。
	 * </font>
	 */
	@XmlElement(name = "coupon_refund_count")
	@JSONField(name = "coupon_refund_count")
	private Integer couponRefundCount;
	/**
	 * 代金券信息
	 * 
	 * @see com.foxinmy.weixin4j.payment.coupon.CouponInfo
	 */
	@ListsuffixResult
	private List<CouponInfo> couponList;

	protected RefundDetail() {
		// jaxb required
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	@JSONField(serialize = false)
	public RefundChannel getFormatRefundChannel() {
		return refundChannel != null ? RefundChannel.valueOf(refundChannel
				.toUpperCase()) : null;
	}

	public int getRefundFee() {
		return refundFee;
	}

	public String getFeeType() {
		return feeType;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatFeeType() {
		return feeType != null ? CurrencyType.valueOf(feeType.toUpperCase())
				: null;
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

	public String getRefundStatus() {
		return refundStatus;
	}

	@JSONField(serialize = false)
	public RefundStatus getFormatRefundStatus() {
		return refundStatus != null ? RefundStatus.valueOf(refundStatus
				.toUpperCase()) : null;
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
	public double getFormatCouponRefundFee() {
		return couponRefundFee != null ? couponRefundFee.intValue() / 100d : 0d;
	}

	public String getRefundFeeType() {
		return refundFeeType;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatRefundFeeType() {
		return refundFeeType != null ? CurrencyType.valueOf(refundFeeType
				.toUpperCase()) : null;
	}

	public int getTotalFee() {
		return totalFee;
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

	public int getCashFee() {
		return cashFee;
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

	public String getCashFeeType() {
		return cashFeeType;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatCashFeeType() {
		return cashFeeType != null ? CurrencyType.valueOf(cashFeeType
				.toUpperCase()) : null;
	}

	public Integer getCashRefundFee() {
		return cashRefundFee;
	}

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatCashRefundFee() {
		return cashRefundFee != null ? cashRefundFee.intValue() / 100d : 0d;
	}

	public String getCashRefundFeeType() {
		return cashRefundFeeType;
	}

	@JSONField(serialize = false)
	public CurrencyType getFormatCashRefundFeeType() {
		return cashRefundFeeType != null ? CurrencyType
				.valueOf(cashRefundFeeType.toUpperCase()) : null;
	}

	public Integer getCouponRefundCount() {
		return couponRefundCount;
	}

	public List<CouponInfo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<CouponInfo> couponList) {
		this.couponList = couponList;
	}

	@Override
	public String toString() {
		return "RefundDetail [outRefundNo=" + outRefundNo + ", refundId="
				+ refundId + ", refundChannel=" + refundChannel
				+ ", refundFee=" + getFormatRefundFee() + ", refundFeeType="
				+ refundFeeType + ", totalFee=" + getFormatTotalFee()
				+ ", feeType=" + feeType + ", cashFee=" + getFormatCashFee()
				+ ", cashFeeType=" + cashFeeType + ", cashRefundFee="
				+ getFormatCashRefundFee() + ", cashRefundFeeType="
				+ cashRefundFeeType + ", refundStatus=" + refundStatus
				+ ", couponRefundFee=" + getFormatCouponRefundFee()
				+ ", couponRefundCount=" + couponRefundCount + ", couponList="
				+ couponList + ", " + super.toString() + "]";
	}
}
