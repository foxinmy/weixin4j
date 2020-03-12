package com.foxinmy.weixin4j.pay.payment.mch;

import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.pay.sign.WeixinSignature;

public abstract class AbstractPayRequest implements MchPayRequest {

	private final String prePayId;
	private final WeixinPayAccount paymentAccount;
	protected final WeixinSignature weixinSignature;

	public AbstractPayRequest(String prePayId, WeixinPayAccount paymentAccount) {
		this.prePayId = prePayId;
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
}