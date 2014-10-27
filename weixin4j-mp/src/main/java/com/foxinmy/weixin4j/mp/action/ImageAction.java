package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.msg.ImageMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 图片消息响应
 * 
 * @className ImageAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.ImageMessage
 */
@Action(msgType = MessageType.image)
public class ImageAction extends DebugAction<ImageMessage> {

}
