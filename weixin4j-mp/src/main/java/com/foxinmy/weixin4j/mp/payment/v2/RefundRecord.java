package com.foxinmy.weixin4j.mp.payment.v2;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

/**
 * V2退款记录
 * 
 * @className RefundRecord
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundDetail
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
	 * 退款笔数
	 */
	@XmlElement(name = "refund_count")
	@JSONField(name = "refund_count")
	private int refundCount;
	/**
	 * 退款详情
	 */
	@ListsuffixResult
	@XmlTransient
	@JSONField(deserialize = false)
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

	public int getRefundCount() {
		return refundCount;
	}

	public List<RefundDetail> getRefundList() {
		return refundList;
	}

	public void setRefundList(List<RefundDetail> refundList) {
		this.refundList = refundList;
	}

	@Override
	public String toString() {
		return "RefundRecord [transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", refundCount=" + refundCount + ", refundList="
				+ refundList + ", " + super.toString() + "]";
	}
}
