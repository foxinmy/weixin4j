package com.foxinmy.weixin4j.type;

/**
 * ID类型
 * 
 * @className IdType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月1日
 * @since JDK 1.6
 * @see
 */
public enum IdType {
	/**
	 * 微信退款单号
	 */
	REFUNDID("refund_id"),
	/**
	 * 微信订单号
	 */
	TRANSACTIONID("transaction_id"),
	/**
	 * 商户订单号
	 */
	TRADENO("out_trade_no"),
	/**
	 * 商户退款号
	 */
	REFUNDNO("out_refund_no"),
	/**
	 * 商户子订单号
	 */
	SUBORDERNO("sub_order_no"),
	/**
	 * 微信子订单号
	 */
	SUBORDERID("sub_order_id");
	private String name;

	IdType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
