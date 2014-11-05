package com.foxinmy.weixin4j.mp.type;

/**
 * 交易状态
 * 
 * @className TradeState
 * @author jy
 * @date 2014年11月2日
 * @since JDK 1.7
 * @see
 */
public enum TradeState {
	SUCCESS, // 支付成功
	REFUND, // 转入退款
	NOTPAY, // 未支付
	CLOSED, // 已关闭
	REVOKED, // 已撤销
	USERPAYING, // 用户支付中
	NOPAY, // 未支付(输入密码或 确认支付超时)
	PAYERROR;// 支付失败(其他 原因,如银行返回失败)
}
