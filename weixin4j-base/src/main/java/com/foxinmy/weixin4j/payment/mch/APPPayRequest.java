package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;

/**
 * APP支付
 * 
 * @className APPPayRequest
 * @author jy
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 * @see com.foxinmy.weixin4j.payment.PayRequest
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=8_1">APP支付</a>
 */
public class APPPayRequest extends AbstractPayRequest {

	public APPPayRequest(String prePayId, WeixinPayAccount payAccount) {
		super(prePayId, payAccount);
	}

	@Override
	public TradeType getTradeType() {
		return TradeType.APP;
	}

	/**
	 * <font color="red">只做查看之用,请不要尝试作为支付请求</font>
	 */
	@Override
	public PayRequest toRequestObject() {
		PayRequest payRequest = new PayRequest(getPayAccount().getId(),
				"Sign=WXPay");
		payRequest.setPrepayId(getPrePayId());
		payRequest.setPartnerId(getPayAccount().getPartnerId());
		return payRequest;
	}

	@Override
	public String toRequestString() {
		PayRequest payRequest = toRequestObject();
		String sign = DigestUtil.MD5(
				String.format("%s&key=%s",
						MapUtil.toJoinString(payRequest, false, true, null),
						getPayAccount().getPaySignKey())).toUpperCase();
		StringBuilder content = new StringBuilder();
		content.append("<xml>");
		content.append(String.format("<appid><![CDATA[%s]]></appid>",
				payRequest.getAppId()));
		content.append(String.format("<partnerid><![CDATA[%s]]></partnerid>",
				payRequest.getPartnerId()));
		content.append(String.format("<prepayid><![CDATA[%s]]></prepayid>",
				payRequest.getPrepayId()));
		content.append(String.format("<package><![CDATA[%s]]></package>",
				payRequest.getPackageInfo()));
		content.append(String.format("<noncestr><![CDATA[%s]]></noncestr>",
				payRequest.getNonceStr()));
		content.append(String.format("<timestamp><![CDATA[%s]]></timestamp>",
				payRequest.getTimeStamp()));
		content.append(String.format("<sign><![CDATA[%s]]></sign>", sign));
		content.append("</xml>");
		return content.toString();
	}
}
