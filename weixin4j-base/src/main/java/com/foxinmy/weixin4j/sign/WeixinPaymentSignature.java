package com.foxinmy.weixin4j.sign;

import com.foxinmy.weixin4j.util.DigestUtil;

/**
 * 微信支付签名实现
 * 
 * @className WeixinPaymentSignature
 * @author jy
 * @date 2016年3月26日
 * @since JDK 1.6
 * @see
 */
public class WeixinPaymentSignature extends AbstractWeixinSignature {
	/**
	 * 支付密钥
	 */
	private final String paySignKey;

	public WeixinPaymentSignature(String paySignKey) {
		this.paySignKey = paySignKey;
	}

	@Override
	public String sign(Object obj) {
		StringBuilder sb = join(obj).append("&key=").append(paySignKey);
		return DigestUtil.MD5(sb.toString()).toUpperCase();
	}
}
