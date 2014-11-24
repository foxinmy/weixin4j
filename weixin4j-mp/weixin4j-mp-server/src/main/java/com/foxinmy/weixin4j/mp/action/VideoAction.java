package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.VideoMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 视频消息处理
 * 
 * @className VideoAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.VideoMessage
 */
@ActionAnnotation(msgType = MessageType.video)
public class VideoAction extends DebugAction<VideoMessage> {

}
