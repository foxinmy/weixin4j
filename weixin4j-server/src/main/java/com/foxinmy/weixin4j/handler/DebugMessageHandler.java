package com.foxinmy.weixin4j.handler;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

public class DebugMessageHandler extends MessageHandlerAdapter {

	public static final DebugMessageHandler global = new DebugMessageHandler();
	
	private DebugMessageHandler(){
		
	}
	
	@Override
	public WeixinResponse doHandle(WeixinRequest request, WeixinMessage message)
			throws WeixinException {
		return new TextResponse(String.format("%s,%s", request,
				message));
	}
}
