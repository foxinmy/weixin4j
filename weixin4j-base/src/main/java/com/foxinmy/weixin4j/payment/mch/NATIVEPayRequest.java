package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;

/**
 * NATIVE扫码支付(模式二)
 * 
 * @className NATIVEPayRequest
 * @author jy
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 * @see com.foxinmy.weixin4j.payment.PayRequest
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_5">NATIVE扫码支付(模式二)</a>
 */
public class NATIVEPayRequest extends AbstractPayRequest {

	public NATIVEPayRequest(PrePay prePay, WeixinPayAccount payAccount) {
		super(prePay, payAccount);
	}

	/**
	 * <font color="red">只做查看之用,请不要尝试作为支付请求</font>
	 */
	@Override
	public PayRequest toRequestObject() {
		return new PayRequest(getPayAccount().getId(), "code_url="
				+ getPrePay().getCodeUrl());
	}

	@Override
	public String toRequestString() {
		return getPrePay().getCodeUrl();
	}
}
