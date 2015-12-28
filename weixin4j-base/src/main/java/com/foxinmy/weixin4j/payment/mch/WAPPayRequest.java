package com.foxinmy.weixin4j.payment.mch;

import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.payment.PayURLConsts;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.URLEncodingUtil;

/**
 * WAP支付
 * 
 * @className WAPPayRequest
 * @author jy
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 * @see com.foxinmy.weixin4j.payment.PayRequest
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/wap.php?chapter=15_1">WAP支付</a>
 */
public class WAPPayRequest extends AbstractPayRequest {

	public WAPPayRequest(String prePayId, WeixinPayAccount payAccount) {
		super(prePayId, payAccount);
	}

	/**
	 * <font color="red">只做查看之用,请不要尝试作为支付请求</font>
	 */
	@Override
	public PayRequest toRequestObject() {
		PayRequest payRequest = new PayRequest(getPayAccount().getId(), "WAP");
		payRequest.setPrepayId(getPrePayId());
		return payRequest;
	}

	@Override
	public String toRequestString() {
		PayRequest payRequest = toRequestObject();
		String original = MapUtil.toJoinString(payRequest, true, true, null);
		String sign = DigestUtil.MD5(
				String.format("%s&key=%s", original, getPayAccount()
						.getPaySignKey())).toUpperCase();
		return String.format(PayURLConsts.MCH_WAP_URL, URLEncodingUtil
				.encoding(String.format("%s&sign=%s", original, sign),
						Consts.UTF_8, true));
	}
}
