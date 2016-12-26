package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.type.TradeType;

/**
 * MICROPAY刷卡支付
 * 
 * @className MICROPayRequest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 * @see com.foxinmy.weixin4j.payment.PayRequest
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=5_1">刷卡支付</a>
 */
public class MICROPayRequest extends Order implements MchPayRequest {

	private static final long serialVersionUID = 6147576305404111278L;

	@XmlTransient
	@JSONField(serialize = false)
	private WeixinPayAccount paymentAccount;

	protected MICROPayRequest() {
		// jaxb required
	}

	@Override
	@JSONField(serialize = false)
	public TradeType getPaymentType() {
		return TradeType.MICROPAY;
	}

	/**
	 * <font color="red">返回null,请不要尝试作为支付请求</font>
	 */
	@Override
	@JSONField(serialize = false)
	public String toRequestString() {
		return null;
	}

	/**
	 * <font color="red">返回null,请不要尝试作为支付请求</font>
	 */
	@JSONField(serialize = false)
	@Override
	public PayRequest toRequestObject() {
		return null;
	}

	/**
	 * <font color="red">返回null,请不要尝试作为支付请求</font>
	 */
	@JSONField(serialize = false)
	@Override
	public String getPrePayId() {
		return null;
	}

	public void setPaymentAccount(WeixinPayAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	@Override
	public WeixinPayAccount getPaymentAccount() {
		return this.paymentAccount;
	}
}
