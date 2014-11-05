package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.mp.response.TextResponse;
import com.foxinmy.weixin4j.msg.BaseMessage;

/**
 * 调试输出用户消息
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
	public TextResponse execute(M message) {
		return new TextResponse(message.toString(), message);
	}
}
