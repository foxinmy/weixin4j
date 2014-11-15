package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.mp.response.BaseResponse;
import com.foxinmy.weixin4j.msg.BaseMessage;

/**
 * 回复一个空字符串 而不是一个XML结构体中content字段的内容为空
 * 
 * @className BlankAction
 * @author jy.hu
 * @date 2014年10月2日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.action.AbstractAction
 */
public class BlankAction<M extends BaseMessage> extends AbstractAction<M> {

	@Override
	public BaseResponse execute(M inMessage) {
		return null;
	}
}
