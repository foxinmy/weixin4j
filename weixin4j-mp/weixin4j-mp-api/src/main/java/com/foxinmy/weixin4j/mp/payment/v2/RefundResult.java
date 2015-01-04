package com.foxinmy.weixin4j.mp.payment.v2;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * V2退款申请结果
 * 
 * @className RefundResult
 * @author jy
 * @date 2014年11月6日
 * @since JDK 1.7
 * @see
 */
@XStreamAlias("xml")
public class RefundResult extends RefundDetail {

	private static final long serialVersionUID = -3687863914168618620L;

	@XStreamAlias("transaction_id")
	@JSONField(name = "transaction_id")
	private String transactionId; // 微信订单号
	@XStreamAlias("out_trade_no")
	@JSONField(name = "out_trade_no")
	private String outTradeNo;// 商户系统内部的订单号

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	@Override
	public String toString() {
		return "RefundResult [transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", " + super.toString() + "]";
	}
}
