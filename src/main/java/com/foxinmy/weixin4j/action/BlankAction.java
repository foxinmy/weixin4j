package com.foxinmy.weixin4j.action;

import com.foxinmy.weixin4j.msg.BaseMessage;

/**
 * 返回空白消息
 * 
 * @className BlankAction
 * @author jy.hu
 * @date 2014年10月2日
 * @since JDK 1.7
 * @see
 */
public abstract class BlankAction<M extends BaseMessage> extends
		AbstractAction<M> {

	private final String BLANK = "";

	@Override
	public String execute(M message) {
		return BLANK;
	}
}
