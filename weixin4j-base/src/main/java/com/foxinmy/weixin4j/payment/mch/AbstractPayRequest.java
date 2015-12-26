package com.foxinmy.weixin4j.payment.mch;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.model.WeixinPayAccount;

public abstract class AbstractPayRequest implements MchPayRequest {

	private final PrePay prePay;
	private final WeixinPayAccount payAccount;

	public AbstractPayRequest(PrePay prePay, WeixinPayAccount payAccount) {
		this.prePay = prePay;
		this.payAccount = payAccount;
	}

	@Override
	public PrePay getPrePay() {
		return this.prePay;
	}

	@Override
	public WeixinPayAccount getPayAccount() {
		return this.payAccount;
	}

	@Override
	public String toRequestString() {
		return JSON.toJSONString(toRequestObject());
	}
}
