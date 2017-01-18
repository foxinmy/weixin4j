package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.type.TradeType;

/**
 * 支付请求接口
 * 
 * @className MchPayRequest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see JSAPIPayRequest JS支付
 * @see NATIVEPayRequest 扫码支付
 * @see MICROPayRequest 刷卡支付
 * @see APPPayRequest APP支付
 * @see WAPPayRequest WAP支付
 */
public interface MchPayRequest {
	/**
	 * 预支付交易ID
	 * 
	 * @return
	 */
	public String getPrePayId();

	/**
	 * 支付账号
	 * 
	 * @return
	 */
	public WeixinPayAccount getPaymentAccount();

	/**
	 * 支付类型
	 * 
	 * @return
	 */
	public TradeType getPaymentType();

	/**
	 * 支付请求字符串
	 * 
	 * @return
	 */
	public String toRequestString();

	/**
	 * 支付请求对象
	 * 
	 * @return
	 */
	public PayRequest toRequestObject();
}
