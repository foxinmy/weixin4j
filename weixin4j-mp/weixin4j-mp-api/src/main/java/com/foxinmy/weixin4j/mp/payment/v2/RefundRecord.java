package com.foxinmy.weixin4j.mp.payment.v2;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * V2退款记录
 * 
 * @className RefundRecord
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.payment.v2.RefundDetail
 */
@XStreamAlias("xml")
public class RefundRecord extends ApiResult {

	private static final long serialVersionUID = -2971132874939642721L;

	@XStreamAlias("transaction_id")
	@JSONField(name = "transaction_id")
	private String transactionId;// 微信订单号
	@XStreamAlias("out_trade_no")
	@JSONField(name = "out_trade_no")
	private String outTradeNo;// 商户订单号
	@XStreamAlias("refund_count")
	@JSONField(name = "refund_count")
	private int count;// 退款笔数
	@XStreamOmitField
	@JSONField(serialize = false, deserialize = false)
	private List<RefundDetail> details; // 退款详情

	public String getTransactionId() {
		return transactionId;
	}

	public String getOutTradeNo() {
		return StringUtils.isNotBlank(outTradeNo) ? outTradeNo : null;
	}

	public int getCount() {
		return count;
	}

	public List<RefundDetail> getDetails() {
		return details;
	}

	@Override
	public String toString() {
		return "RefundRecord [transactionId=" + transactionId + ", outTradeNo="
				+ outTradeNo + ", count=" + count + ", details=" + details
				+ ", " + super.toString() + "]";
	}
}
