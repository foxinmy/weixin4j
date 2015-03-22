package com.foxinmy.weixin4j.action;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.msg.model.Text;
import com.foxinmy.weixin4j.response.ResponseMessage;

/**
 * 调试输出用户消息
 * 
 * @className DebugAction
 * @author jy
 * @date 2014年10月8日
 * @since JDK 1.7
 * @see
 */
public class DebugAction<M extends BaseMsg> extends AbstractAction<M> {

	@Override
	public ResponseMessage execute(M message) {
		return new ResponseMessage(new Text(message.toString()), message);
	}
}
