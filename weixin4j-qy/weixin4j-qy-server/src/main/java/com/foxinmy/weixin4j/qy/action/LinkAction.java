package com.foxinmy.weixin4j.qy.action;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.LinkMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 链接消息处理
 * 
 * @className LinkAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.LinkMessage
 */
@ActionAnnotation(msgType = MessageType.link)
public class LinkAction extends DebugAction<LinkMessage> {

}
