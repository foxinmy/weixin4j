package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.action.DebugAction;
import com.foxinmy.weixin4j.action.mapping.ActionAnnotation;
import com.foxinmy.weixin4j.msg.VoiceMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 语音消息处理
 * 
 * @className VoiceAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.VoiceMessage
 */
@ActionAnnotation(msgType = MessageType.voice)
public class VoiceAction extends DebugAction<VoiceMessage> {

}
