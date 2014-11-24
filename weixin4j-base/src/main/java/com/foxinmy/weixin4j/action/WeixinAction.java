package com.foxinmy.weixin4j.action;

import org.dom4j.DocumentException;

import com.foxinmy.weixin4j.response.ResponseMessage;

/**
 * 消息处理接口
 * 
 * @className Action
 * @author jy.hu
 * @date 2014年10月2日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.action.AbstractAction
 * @see com.foxinmy.weixin4j.action.BlankAction
 * @see com.foxinmy.weixin4j.action.DebugAction
 */
public interface WeixinAction {
	public ResponseMessage execute(String inMsg) throws DocumentException;
}
