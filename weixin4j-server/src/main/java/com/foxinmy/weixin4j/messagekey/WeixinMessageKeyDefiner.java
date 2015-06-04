package com.foxinmy.weixin4j.messagekey;

import com.foxinmy.weixin4j.type.AccountType;

/**
 * 微信消息key的定义
 * 
 * @className WeixinMessageKeyDefiner
 * @author jy
 * @date 2015年5月18日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.messagekey.DefaultMessageKeyDefiner
 */
public interface WeixinMessageKeyDefiner {

	/**
	 * 声明messageKey
	 * 
	 * @param messageType
	 *            消息类型
	 * @param eventType
	 *            事件类型
	 * @param accountType
	 *            账号类型
	 * @return messageKey
	 */
	public String defineMessageKey(String messageType, String eventType,
			AccountType accountType);
}
