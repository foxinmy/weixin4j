package com.foxinmy.weixin4j.sign;


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
	public boolean encoder();

	/**
	 * 是否转换小写
	 * 
	 * @return
	 */
	public boolean lowerCase();

	/**
	 * 签名
	 * 
	 * @param obj
	 * @return
	 */
	public String sign(Object obj);
}
