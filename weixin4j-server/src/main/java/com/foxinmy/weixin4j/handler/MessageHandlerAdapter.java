package com.foxinmy.weixin4j.handler;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

@SuppressWarnings("unchecked")
public abstract class MessageHandlerAdapter<M> implements WeixinMessageHandler {

	@Override
	public boolean canHandle(WeixinRequest request, Object message)
			throws WeixinException {
		return canHandle0(request, (M) message);
	}

	public boolean canHandle0(WeixinRequest request, M message)
			throws WeixinException {
		return true;
	}

	@Override
	public WeixinResponse doHandle(WeixinRequest request, Object message)
			throws WeixinException {
		return doHandle0(request, (M) message);
	}

	public abstract WeixinResponse doHandle0(WeixinRequest request, M message)
			throws WeixinException;

}
