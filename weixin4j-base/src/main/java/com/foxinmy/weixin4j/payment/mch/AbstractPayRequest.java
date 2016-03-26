package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.sign.WeixinPaymentSignature;
import com.foxinmy.weixin4j.sign.WeixinSignature;

public abstract class AbstractPayRequest implements MchPayRequest {

	private final String prePayId;
	private final WeixinPayAccount payAccount;
	protected final WeixinSignature weixinSignature;

	public AbstractPayRequest(String prePayId, WeixinPayAccount payAccount) {
		this.prePayId = prePayId;
		this.payAccount = payAccount;
		this.weixinSignature = new WeixinPaymentSignature(payAccount.getPaySignKey());
	}

	@Override
	public String getPrePayId() {
		return this.prePayId;
	}

	@Override
	public WeixinPayAccount getPayAccount() {
		return this.payAccount;
	}
}
