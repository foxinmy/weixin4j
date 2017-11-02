package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.type.TradeType;

/**
 * WAP支付
 * 
 * @className WAPPayRequest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 * @see com.foxinmy.weixin4j.payment.PayRequest
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/wap.php?chapter=15_1">WAP支付</a>
 */
public class WAPPayRequest extends AbstractPayRequest {
	/**
	 * 微信支付URL
	 */
	private final String payUrl;

	public WAPPayRequest(String prePayId, String payUrl,
			WeixinPayAccount payAccount) {
		super(prePayId, payAccount);
		this.payUrl = payUrl;

	}

	@Override
	public TradeType getPaymentType() {
		return TradeType.MWEB;
	}

	/**
	 * <font color="red">只做查看之用,请不要尝试作为支付请求</font>
	 */
	@Override
	public PayRequest toRequestObject() {
		PayRequest payRequest = new PayRequest(getPaymentAccount().getId(),
				getPaymentType().name());
		payRequest.setPrepayId(getPrePayId());
		return payRequest;
	}

	@Override
	public String toRequestString() {
		// PayRequest payRequest = toRequestObject();
		// String original = MapUtil.toJoinString(payRequest, true, true);
		// String sign = DigestUtil.MD5(
		// String.format("%s&key=%s", original, getPaymentAccount()
		// .getPaySignKey())).toUpperCase();
		// return String.format("weixin://wap/pay?%s",
		// URLEncodingUtil.encoding(
		// String.format("%s&sign=%s", original, sign),
		// Consts.UTF_8, true));
		return this.payUrl;
	}
}
