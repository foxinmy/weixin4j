package com.foxinmy.weixin4j.type;

/**
 * 企业付款检查收款人姓名的策略
 * 
 * @className MPPaymentCheckType
 * @author jy
 * @date 2015年4月1日
 * @since JDK 1.7
 * @see
 */
public enum MPPaymentCheckNameType {
	/**
	 * 不校验真实姓名
	 */
	NO_CHECK,
	/**
	 * 强校验真实姓名（未实名认证的用户会校验失败，无法转账）
	 */
	FORCE_CHECK,
	/**
	 * 针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
	 */
	OPTION_CHECK;
}
