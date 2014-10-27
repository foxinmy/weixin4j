package com.foxinmy.weixin4j.action;

import com.foxinmy.weixin4j.msg.in.ImageMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 图片消息响应
 * 
 * @className ImageAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.in.ImageMessage
 */
@Action(msgType = MessageType.image)
public class ImageAction extends DebugAction<ImageMessage> {

}
