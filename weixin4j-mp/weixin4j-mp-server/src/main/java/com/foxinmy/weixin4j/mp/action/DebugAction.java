package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.mp.message.ResponseMessage;
import com.foxinmy.weixin4j.msg.model.Text;

/**
 * 调试输出用户消息
 * 
 * @className DebugAction
 * @author jy
 * @date 2014年10月8日
 * @since JDK 1.7
 * @see
 */
public abstract class DebugAction<M extends BaseMsg> extends AbstractAction<M> {

	@Override
	public ResponseMessage execute(M message) {
		return new ResponseMessage(new Text(message.toString()), message);
	}
}
