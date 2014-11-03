package com.foxinmy.weixin4j.mp.payment;

/**
 * ID类型
 * 
 * @className IdType
 * @author jy
 * @date 2014年11月1日
 * @since JDK 1.7
 * @see
 */
public enum IdType {
	REFUNDID("refund_id"), // 微信退款单号
	TRANSACTIONID("transaction_id"), // 微信订单号
	ORDERNO("out_trade_no"), // 商户订单号
	REFUNDNO("out_refund_no"); // 商户退款号
	private String name;

	IdType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
