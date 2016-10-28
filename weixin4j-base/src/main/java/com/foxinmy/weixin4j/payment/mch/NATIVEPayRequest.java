package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.type.TradeType;

/**
 * NATIVE扫码支付(模式二)
 * 
 * @className NATIVEPayRequest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 * @see com.foxinmy.weixin4j.payment.PayRequest
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=6_1">NATIVE扫码支付(模式二)</a>
 */
public class NATIVEPayRequest extends AbstractPayRequest {

	private final String codeUrl;

	public NATIVEPayRequest(String prePayId, String codeUrl,
			WeixinPayAccount payAccount) {
		super(prePayId, payAccount);
		this.codeUrl = codeUrl;
	}

	@Override
	public TradeType getPaymentType() {
		return TradeType.NATIVE;
	}

	/**
	 * <font color="red">只做查看之用,请不要尝试作为支付请求</font>
	 */
	@Override
	public PayRequest toRequestObject() {
		return new PayRequest(getPaymentAccount().getId(), "code_url=" + codeUrl);
	}

	@Override
	public String toRequestString() {
		return this.codeUrl;
	}
}
