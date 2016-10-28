package com.foxinmy.weixin4j.payment.mch;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.xml.ListsuffixResult;

/**
 * 退款记录
 * 
 * @className RefundRecord
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月1日
 * @since JDK 1.6
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundRecord extends MerchantTradeResult {

	private static final long serialVersionUID = -2971132874939642721L;
	/**
	 * 退款笔数
	 */
	@XmlElement(name = "refund_count")
	@JSONField(name = "refund_count")
	private int refundCount;
	/**
	 * 退款总金额,单位为分,可以做部分退款
	 */
	@XmlElement(name = "refund_fee")
	@JSONField(name = "refund_fee")
	private int refundFee;
	/**
	 * 退款详情
	 * 
	 * @see RefundDetail
	 */
	@ListsuffixResult({ ".*(_\\d)$" })
	private List<RefundDetail> refundList;

	protected RefundRecord() {
		// jaxb required
	}

	public int getRefundCount() {
		return refundCount;
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

	public List<RefundDetail> getRefundList() {
		return refundList;
	}

	public void setRefundList(List<RefundDetail> refundList) {
		this.refundList = refundList;
	}

	@Override
	public String toString() {
		return "RefundRecord [refundCount=" + refundCount + ", refundFee="
				+ refundFee + ", refundList=" + refundList + ", "
				+ super.toString() + "]";
	}
}
