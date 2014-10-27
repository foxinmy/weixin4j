package com.foxinmy.weixin4j.mp.action;

import com.foxinmy.weixin4j.msg.VoiceMessage;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 语音消息响应
 * 
 * @className VoiceAction
 * @author jy
 * @date 2014年10月9日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.msg.VoiceMessage
 */
@Action(msgType = MessageType.voice)
public class VoiceAction extends DebugAction<VoiceMessage> {

}
