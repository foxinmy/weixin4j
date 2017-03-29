package com.zone.weixin4j.dispatcher;

import com.zone.weixin4j.request.WeixinMessage;

/**
 * 微信消息匹配
 * 
 * @className WeixinMessageMatcher
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月17日
 * @since JDK 1.6
 * @see DefaultMessageMatcher
 */
public interface WeixinMessageMatcher {
	/**
	 * 匹配消息类型
	 * 
	 * @param messageKey
	 *            消息key
	 * @return 消息类型
	 */
	public Class<? extends WeixinMessage> match(WeixinMessageKey messageKey);

	/**
	 * 注册消息类型「程序没有及时更新而微信又产生了新的消息类型」
	 * 
	 * @param messageKey
	 *            消息key
	 * @param messageClass
	 *            消息类型
	 */
	public void regist(WeixinMessageKey messageKey,
                       Class<? extends WeixinMessage> messageClass);
}
