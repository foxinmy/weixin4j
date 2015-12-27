package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;

public abstract class AbstractPayRequest implements MchPayRequest {

	private final String prePayId;
	private final WeixinPayAccount payAccount;

	public AbstractPayRequest(String prePayId, WeixinPayAccount payAccount) {
		this.prePayId = prePayId;
		this.payAccount = payAccount;
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
