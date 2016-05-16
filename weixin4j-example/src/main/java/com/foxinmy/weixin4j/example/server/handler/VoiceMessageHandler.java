package com.foxinmy.weixin4j.example.server.handler;

import org.springframework.stereotype.Component;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.MessageHandlerAdapter;
import com.foxinmy.weixin4j.message.VoiceMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 只处理语音消息
 * 
 * @className VoiceMessageHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年11月18日
 * @since JDK 1.7
 * @see
 */
@Component
public class VoiceMessageHandler extends MessageHandlerAdapter<VoiceMessage> {

	@Override
	public WeixinResponse doHandle0(WeixinRequest request, VoiceMessage message)
			throws WeixinException {
		/**
		 * 返回一段文字给用户
		 */
		return new TextResponse("你讲了一句话");
	}
}
