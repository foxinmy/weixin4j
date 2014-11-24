package com.foxinmy.weixin4j.qy.action;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.ImageMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 图片消息处理
 * 
 * @className ImageAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.ImageMessage
 */
@ActionAnnotation(msgType = MessageType.image)
public class ImageAction extends DebugAction<ImageMessage> {

}
