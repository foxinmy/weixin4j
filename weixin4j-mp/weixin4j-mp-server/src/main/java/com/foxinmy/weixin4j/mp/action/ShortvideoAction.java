package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.VideoMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 小视频消息处理
 * 
 * @className ShortvideoAction
 * @author jy
 * @date 2015年4月6日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.VideoMessage
 */
@ActionAnnotation(msgType = MessageType.shortvideo)
public class ShortvideoAction extends DebugAction<VideoMessage> {

}
