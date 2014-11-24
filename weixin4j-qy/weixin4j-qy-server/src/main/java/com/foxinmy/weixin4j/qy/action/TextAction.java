package com.foxinmy.weixin4j.qy.action;

import com.foxinmy.weixin4j.action.AbstractAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.TextMessage;
import com.foxinmy.weixin4j.msg.model.Text;
import com.foxinmy.weixin4j.response.ResponseMessage;
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
@ActionAnnotation(msgType = MessageType.text)
public class TextAction extends AbstractAction<TextMessage> {

	@Override
	public ResponseMessage execute(TextMessage inMessage) {
		return new ResponseMessage(new Text("Hello World!"), inMessage);
	}
}
