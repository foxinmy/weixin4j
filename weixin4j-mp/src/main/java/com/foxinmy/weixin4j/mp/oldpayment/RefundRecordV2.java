package com.foxinmy.weixin4j.mp.oldpayment;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

/**
 * V2退款记录
 * 
 * @className RefundRecordV2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月1日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.mp.oldpayment.RefundDetailV2
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundRecordV2 extends ApiResultV2 {

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
	 * 退款笔数
	 */
	@XmlElement(name = "refund_count")
	@JSONField(name = "refund_count")
	private int refundCount;
	/**
	 * 退款详情
	 */
	@ListsuffixResult
	private List<RefundDetailV2> refundList;

	protected RefundRecordV2() {
		// jaxb required
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public int getRefundCount() {
		return refundCount;
	}

	public List<RefundDetailV2> getRefundList() {
		return refundList;
	}

	public void setRefundList(List<RefundDetailV2> refundList) {
		this.refundList = refundList;
	}

	@Override
	public String toString() {
		return "RefundRecord [transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", refundCount=" + refundCount + ", refundList="
				+ refundList + ", " + super.toString() + "]";
	}
}
