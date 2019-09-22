package com.foxinmy.weixin4j.pay.sign;

import com.foxinmy.weixin4j.pay.type.SignType;

/**
 * 微信签名
 *
 * @className WeixinSignature
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月26日
 * @since JDK 1.6
 * @see
 */
public interface WeixinSignature {
	/**
	 * 是否编码
	 *
	 * @return
	 */
	boolean encoder();

	/**
	 * 是否转换小写
	 *
	 * @return
	 */
	boolean lowerCase();

	/**
	 * 签名（默认的MD5签名）
	 *
	 * @param obj
	 * @return
	 */
	String sign(Object obj);

	/**
	 * 签名（指定签名算法）
	 *
	 * @param obj
	 * @param signType
	 * @return
	 */
	String sign(Object obj, SignType signType);
}
