package com.foxinmy.weixin4j.handler;

import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;

public abstract class MessageHandlerAdapter implements WeixinMessageHandler {

	@Override
	public boolean canHandle(WeixinRequest request, WeixinMessage message) {
		return true;
	}
}
