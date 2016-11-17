package com.foxinmy.weixin4j.mp.oldpayment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * V2退款申请结果
 * 
 * @className RefundResultV2
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月6日
 * @since JDK 1.6
 * @see
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundResultV2 extends RefundDetailV2 {

	private static final long serialVersionUID = -3687863914168618620L;

	/**
	 * 微信订单号
	 */
	@XmlElement(name = "transaction_id")
	@JSONField(name = "transaction_id")
	private String transactionId;
	/**
	 * 商户系统内部的订单号
	 */
	@XmlElement(name = "out_trade_no")
	@JSONField(name = "out_trade_no")
	private String outTradeNo;

	protected RefundResultV2() {
		// jaxb required
	}
	
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
