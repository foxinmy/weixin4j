package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.model.BaseMsg;
import com.foxinmy.weixin4j.mp.message.ResponseMessage;

/**
 * 回复一个空字符串 而不是一个XML结构体中content字段的内容为空
 * 
 * @className BlankAction
 * @author jy.hu
 * @date 2014年10月2日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.action.AbstractAction
 */
public class BlankAction<M extends BaseMsg> extends AbstractAction<M> {

	@Override
	public ResponseMessage execute(M inMessage) {
		return null;
	}
}
