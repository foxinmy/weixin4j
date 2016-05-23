package com.foxinmy.weixin4j.payment.mch;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.CustomsSatus;
import com.foxinmy.weixin4j.util.DateUtil;

/**
 * 报关结果
 * 
 * @className CustomsOrderResult
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月27日
 * @since JDK 1.6
 * @see
 */
public class CustomsOrderResult extends MerchantResult {

	private static final long serialVersionUID = 799510373861612386L;
	/**
	 * 状态码
	 */
	private String state;
	/**
	 * 微信支付订单号
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
	 * 商户子订单号
	 */
	@XmlElement(name = "sub_order_no")
	@JSONField(name = "sub_order_no")
	private String subOrderNo;
	/**
	 * 微信子订单号
	 * 
	 */
	@XmlElement(name = "sub_order_id")
	@JSONField(name = "sub_order_id")
	private String subOrderId;
	/**
	 * 最后更新时间
	 */
	@XmlElement(name = "modify_time")
	@JSONField(name = "modify_time")
	private String modifyTime;

	public String getState() {
		return state;
	}

	@JSONField(serialize = false)
	public CustomsSatus getFormatState() {
		return CustomsSatus.valueOf(state.toUpperCase());
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSubOrderNo() {
		return subOrderNo;
	}

	public void setSubOrderNo(String subOrderNo) {
		this.subOrderNo = subOrderNo;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	@JSONField(serialize = false)
	public Date getFormatModifyTime() {
		return DateUtil.parse2yyyyMMddHHmmss(modifyTime);
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "CustomsOrderResult [state=" + state + ", transactionId="
				+ transactionId + ", outTradeNo=" + outTradeNo
				+ ", subOrderNo=" + subOrderNo + ", subOrderId=" + subOrderId
				+ ", modifyTime=" + modifyTime + "]";
	}
}
