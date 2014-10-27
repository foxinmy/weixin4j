package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.msg.LinkMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 链接消息响应
 * 
 * @className LinkAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.LinkMessage
 */
@Action(msgType = MessageType.link)
public class LinkAction extends DebugAction<LinkMessage> {

}
