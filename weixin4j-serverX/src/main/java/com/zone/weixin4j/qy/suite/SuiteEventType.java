package com.zone.weixin4j.qy.suite;

/**
 * 应用套件回调事件
 * 
 * @className SuiteEventType
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月21日
 * @since JDK 1.6
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%9B%9E%E8%B0%83%E5%8D%8F%E8%AE%AE">第三方回调协议</a>
 */
public enum SuiteEventType {
	/**
	 * 推送ticket
	 */
	suite_ticket,
	/**
	 * 变更授权
	 */
	change_auth,
	/**
	 * 取消授权
	 */
	cancel_auth;
}
