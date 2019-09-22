package com.foxinmy.weixin4j.pay.sign;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.pay.type.SignType;
import com.foxinmy.weixin4j.util.DigestUtil;

import java.security.InvalidKeyException;

/**
 * 微信支付签名实现
 *
 * @className WeixinPaymentSignature
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月26日
 * @since JDK 1.6
 * @see <a
 *      href="https://pay.weixin.qq.com/wiki/doc/api/external/jsapi.php?chapter=4_3">支付签名说明</a>
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

	@Override
	public String sign(Object obj, SignType signType) {
		if(signType==null){
			return sign(obj);
		}
		switch (signType){
			case HMAC$SHA256:
				StringBuilder sb = join(obj).append("&key=").append(paySignKey);
				try {
					return DigestUtil.HMACSHA256(sb.toString(), paySignKey).toUpperCase();
				}catch (InvalidKeyException e){
					throw new RuntimeException("商户支付密钥有误", e);
				}
			default:
				return sign(obj);
		}
	}
}
