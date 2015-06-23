package com.foxinmy.weixin4j.suite;

import com.foxinmy.weixin4j.response.SingleResponse;

/**
 * 处理第三方应用套件请求
 * 
 * @className SuiteMessageHandler
 * @author jy
 * @date 2015年6月23日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%9B%9E%E8%B0%83%E5%8D%8F%E8%AE%AE">套件回调协议</a>
 */
public interface SuiteMessageHandler {
	/**
	 * 处理套件消息
	 * 
	 * @param suiteMessage
	 * @return
	 */
	public SingleResponse handle(WeixinSuiteMessage suiteMessage);
}
