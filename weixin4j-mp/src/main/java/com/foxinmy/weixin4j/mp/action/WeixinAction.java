package com.foxinmy.weixin4j.mp.action;

import org.dom4j.DocumentException;

/**
 * 消息处理接口
 * 
 * @className Action
 * @author jy.hu
 * @date 2014年10月2日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.action.AbstractAction
 * @see com.foxinmy.weixin4j.mp.action.BlankAction
 * @see com.foxinmy.weixin4j.mp.action.DebugAction
 */
public interface WeixinAction {
	public String execute(String msg) throws DocumentException;
}
