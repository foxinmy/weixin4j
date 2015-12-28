package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;

/**
 * 支付请求接口
 * 
 * @className MchPayRequest
 * @author jy
 * @date 2015年12月25日
 * @since JDK 1.7
 * @see JSAPIPayRequest JS支付
 * @see NATIVEPayRequest 扫码支付
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
	 * 商户信息
	 * 
	 * @return
	 */
	public WeixinPayAccount getPayAccount();

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
