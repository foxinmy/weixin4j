package com.foximy.weixin4j.example.server.handler;

import org.springframework.stereotype.Component;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.MessageHandlerAdapter;
import com.foxinmy.weixin4j.message.TextMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 输入 hello 回复 world
 * 
 * @className HelloMessageHandler
 * @author jy
 * @date 2015年12月27日
 * @since JDK 1.7
 * @see
 */
@Component
public class HelloMessageHandler extends MessageHandlerAdapter<TextMessage> {
	@Override
	public boolean canHandle0(WeixinRequest request, TextMessage message)
			throws WeixinException {
		return message.getContent().equalsIgnoreCase("hello");
	}

	@Override
	public WeixinResponse doHandle0(WeixinRequest request, TextMessage message)
			throws WeixinException {
		/**
		 * 返回用户「world」文本
		 */
		return new TextResponse("world");
	}

	/**
	 * 提高权重 > TextMessageHandler
	 */
	@Override
	public int weight() {
		return 2;
	}
}
