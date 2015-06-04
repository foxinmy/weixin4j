package com.foxinmy.weixin4j.messagekey;

import com.foxinmy.weixin4j.type.AccountType;
import com.foxinmy.weixin4j.type.MessageType;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 默认的messageKey实现
 * 
 * <p>
 * 普通消息key=messageType.toLowerCase();</br>
 * 事件消息key=accountType:eventType:messageType.toLowerCase();
 * </p>
 * 
 * @className DefaultMessageKeyDefiner
 * @author jy
 * @date 2015年5月18日
 * @since JDK 1.7
 * @see
 */
public class DefaultMessageKeyDefiner implements WeixinMessageKeyDefiner {

	private static final String MESSAGEKEY_SEPARATOR = ":";

	@Override
	public String defineMessageKey(String messageType, String eventType,
			AccountType accountType) {
		StringBuilder messageKey = new StringBuilder();
		if (!StringUtil.isBlank(messageType)) {
			messageKey.append(messageType.toLowerCase());
			if (!messageType.trim().equalsIgnoreCase(MessageType.event.name())) {
				return messageKey.toString();
			}
		}
		if (accountType != null) {
			messageKey.insert(0, String.format("%s%s", accountType.name()
					.toLowerCase(), MESSAGEKEY_SEPARATOR));
		}
		if (!StringUtil.isBlank(eventType)) {
			messageKey.append(MESSAGEKEY_SEPARATOR).append(
					eventType.toLowerCase());
		}
		return messageKey.toString();
	}
}
