package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.mp.mapping.Action;
import com.foxinmy.weixin4j.mp.response.TextResponse;
import com.foxinmy.weixin4j.msg.TextMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 文字消息处理
 * 
 * @className TextAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.TextMessage
 */
@Action(msgType = MessageType.text)
public class TextAction extends AbstractAction<TextMessage> {

	@Override
	public TextResponse execute(TextMessage inMessage) {
		return new TextResponse("Hello World!", inMessage);
	}
}
