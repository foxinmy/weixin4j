package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.sign.WeixinSignature;

public abstract class AbstractPayRequest implements MchPayRequest {

	private final String prePayId;
	private final WeixinPayAccount paymentAccount;
	protected final WeixinSignature weixinSignature;

	protected final String payResponse;

	public AbstractPayRequest(String prePayId, String payResponse, WeixinPayAccount paymentAccount) {
		this.prePayId = prePayId;
		this.payResponse = payResponse;
		this.paymentAccount = paymentAccount;
		this.weixinSignature = new WeixinPaymentSignature(paymentAccount.getPaySignKey());

	}

	@Override
	public String getPrePayId() {
		return this.prePayId;
	}

	@Override
	public WeixinPayAccount getPaymentAccount() {
		return this.paymentAccount;
	}


	@Override
	public String getResponseString() {
		return payResponse;
	}
}