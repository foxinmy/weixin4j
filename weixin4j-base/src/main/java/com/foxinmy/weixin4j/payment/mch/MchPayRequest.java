package com.foxinmy.weixin4j.payment.mch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.payment.PayRequest;

/**
 * JS支付:get_brand_wcpay_request</br>
 * <p>
 * get_brand_wcpay_request:ok 支付成功<br>
 * get_brand_wcpay_request:cancel 支付过程中用户取消<br>
 * get_brand_wcpay_request:fail 支付失败
 * </p>
 * <p>
 * NATIVE支付:PayRequest.TradeType=NATIVE
 * </p>
 * 
 * @className PayRequest
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MchPayRequest extends PayRequest {

	private static final long serialVersionUID = -5972173459255255197L;
	
	protected MchPayRequest() {
		// jaxb required
	}
	
	public MchPayRequest(PrePay prePay) throws PayException {
		this.setAppId(prePay.getAppId());
		this.setPackageInfo("prepay_id=" + prePay.getPrepayId());
	}

	@Override
	public String toString() {
		return "MchPayRequest [" + super.toString() + "]";
	}
}
