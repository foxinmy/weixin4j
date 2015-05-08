package com.foxinmy.weixin4j.handler;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.BlankResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

public class BlankMessageHandler extends MessageHandlerAdapter {

	public final static BlankMessageHandler global = new BlankMessageHandler();

	private BlankMessageHandler() {

	}

	@Override
	public WeixinResponse doHandle(WeixinRequest request, WeixinMessage message)
			throws WeixinException {
		return BlankResponse.global;
	}
}
