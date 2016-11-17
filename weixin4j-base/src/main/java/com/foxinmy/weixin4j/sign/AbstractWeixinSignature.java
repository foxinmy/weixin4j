package com.foxinmy.weixin4j.sign;

import com.foxinmy.weixin4j.util.MapUtil;

/**
 * 微信签名
 *
 * @className AbstractWeixinSignature
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月26日
 * @since JDK 1.6
 * @see
 */
public abstract class AbstractWeixinSignature implements WeixinSignature {
	/**
	 * 是否编码
	 *
	 * @return 默认false不进行编码
	 */
	@Override
	public boolean encoder() {
		return false;
	}

	/**
	 * 是否转换小写
	 *
	 * @return 默认false不转换小写
	 */
	@Override
	public boolean lowerCase() {
		return false;
	}

	/**
	 * 拼接字符串
	 *
	 * @param obj
	 * @return
	 */
	protected StringBuilder join(Object obj) {
		return new StringBuilder(MapUtil.toJoinString(obj, encoder(),
				lowerCase()));
	}
}
