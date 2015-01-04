package com.foxinmy.weixin4j.mp.payment.v3;

import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.mp.payment.PayRequest;

/**
 * V3 JS支付:get_brand_wcpay_request</br>
 * <p>
 * get_brand_wcpay_request:ok 支付成功<br>
 * get_brand_wcpay_request:cancel 支付过程中用户取消<br>
 * get_brand_wcpay_request:fail 支付失败
 * </p>
 * <p>
 * NATIVE支付:PayRequest.TradeType=NATIVE
 * </p>
 * 
 * @className PayRequestV3
 * @author jy
 * @date 2014年8月17日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.payment.v3.PrePay
 */
public class PayRequestV3 extends PayRequest {

	private static final long serialVersionUID = -5972173459255255197L;
	public PayRequestV3(PrePay prePay) throws PayException {
		this.setAppId(prePay.getAppId());
		this.setPackageInfo("prepay_id=" + prePay.getPrepayId());
	}

	@Override
	public String toString() {
		return "JsPayRequestV3 [" + super.toString() + "]";
	}
}
