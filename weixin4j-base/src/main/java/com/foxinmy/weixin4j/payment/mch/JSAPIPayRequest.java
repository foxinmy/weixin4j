package com.foxinmy.weixin4j.payment.mch;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.util.DigestUtil;

/**
 * 公众号JS支付:get_brand_wcpay_request</br>
 * <p>
 * get_brand_wcpay_request:ok 支付成功<br>
 * get_brand_wcpay_request:cancel 支付过程中用户取消<br>
 * get_brand_wcpay_request:fail 支付失败
 * </p>
 * 
 * @className JSAPIPayRequest
 * @author jy
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 * @see com.foxinmy.weixin4j.payment.PayRequest
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6">网页端调起支付API</a>
 */
public class JSAPIPayRequest extends AbstractPayRequest {

	public JSAPIPayRequest(String prePayId, WeixinPayAccount payAccount) {
		super(prePayId, payAccount);
	}
	
	@Override
	public TradeType getTradeType() {
		return TradeType.JSAPI;
	}

	@Override
	public PayRequest toRequestObject() {
		PayRequest payRequest = new PayRequest(getPayAccount().getId(),
				"prepay_id=" + getPrePayId());
		payRequest.setSignType(SignType.MD5);
		payRequest.setPaySign(DigestUtil.paysignMd5(payRequest, getPayAccount()
				.getPaySignKey()));
		return payRequest;
	}

	@Override
	public String toRequestString() {
		return JSON.toJSONString(toRequestObject());
	}
}
