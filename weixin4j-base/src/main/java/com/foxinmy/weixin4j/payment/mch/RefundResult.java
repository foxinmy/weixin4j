package com.foxinmy.weixin4j.payment.mch;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.mch.RefundChannel;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

/**
 * 退款申请结果
 * 
 * @className RefundResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月6日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundResult extends MerchantTradeResult {

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
	 * 现金退款金额
	 */
	@XmlElement(name = "cash_refund_fee")
	@JSONField(name = "cash_refund_fee")
	private Integer cashRefundFee;
	/**
	 * 退款详情
	 * 
	 * @see RefundDetail
	 */
	@ListsuffixResult({ ".*(_\\d)$" })
	private List<RefundDetail> refundList;

	protected RefundResult() {
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

	/**
	 * <font color="red">调用接口获取单位为分,get方法转换为元方便使用</font>
	 * 
	 * @return 元单位
	 */
	@JSONField(serialize = false)
	public double getFormatRefundFee() {
		return refundFee / 100d;
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

	public List<RefundDetail> getRefundList() {
		return refundList;
	}

	@Override
	public String toString() {
		return "RefundResult [" + super.toString() + ", outRefundNo="
				+ outRefundNo + ", refundId=" + refundId + ", refundChannel="
				+ refundChannel + ", refundFee=" + refundFee
				+ ", cashRefundFee=" + cashRefundFee + ", refundList="
				+ refundList + "]";
	}
}
