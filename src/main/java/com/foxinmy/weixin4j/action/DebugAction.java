package com.foxinmy.weixin4j.action;

import com.foxinmy.weixin4j.msg.BaseMessage;
import com.foxinmy.weixin4j.msg.TextMessage;

/**
 * 显示调试信息
 * 
 * @className DebugAction
 * @author jy
 * @date 2014年10月8日
 * @since JDK 1.7
 * @see
 */
public abstract class DebugAction<M extends BaseMessage> extends
		AbstractAction<M> {

	@Override
	public String execute(M message) {
		BaseMessage response = new TextMessage(message.toString(), message);
		return response.toXml();
	}
}
