package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.mp.response.TextResponse;
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
public class BlankAction<M extends BaseMessage> extends AbstractAction<M> {

	@Override
	public TextResponse execute(M inMessage) {
		return new TextResponse("", inMessage);
	}
}
