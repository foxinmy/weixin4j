package com.foxinmy.weixin4j.payment.mch;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

/**
 * 报关记录
 * 
 * @className CustomsOrderRecord
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月27日
 * @since JDK 1.6
 * @see
 */
public class CustomsOrderRecord extends MerchantResult {

	private static final long serialVersionUID = -1675090110657154049L;
	/**
	 * 微信支付订单号
	 */
	@XmlElement(name = "transaction_id")
	@JSONField(name = "transaction_id")
	private String transactionId;
	/**
	 * 笔数
	 */
	@XmlElement(name = "count")
	@JSONField(name = "count")
	private int orderCount;

	/**
	 * 报关详情
	 * 
	 * @see com.foxinmy.weixin4j.payment.mch.CustomsOrderResult
	 */
	@ListsuffixResult
	private List<CustomsOrderResult> customsOrderList;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public List<CustomsOrderResult> getCustomsOrderList() {
		return customsOrderList;
	}

	public void setCustomsOrderList(List<CustomsOrderResult> customsOrderList) {
		this.customsOrderList = customsOrderList;
	}

	@Override
	public String toString() {
		return "CustomsOrderRecord [transactionId=" + transactionId
				+ ", orderCount=" + orderCount + ", customsOrderList="
				+ customsOrderList + "]";
	}
}
