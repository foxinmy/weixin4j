package com.foxinmy.weixin4j.handler;

import com.foxinmy.weixin4j.dispatcher.WeixinMessageAdapter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

public abstract class MessageHandlerAdapter<M> extends WeixinMessageAdapter<M>
		implements WeixinMessageHandler {

	private M message;

	@Override
	public boolean canHandle(WeixinRequest request, String message)
			throws WeixinException {
		this.message = super.messageRead(message);
		return canHandle0(request, message, this.message);
	}

	public abstract boolean canHandle0(WeixinRequest request, String message,
			M m) throws WeixinException;

	@Override
	public WeixinResponse doHandle(WeixinRequest request, String message)
			throws WeixinException {
		return doHandle0(request, message, this.message);
	}

	public abstract WeixinResponse doHandle0(WeixinRequest request,
			String message, M m) throws WeixinException;

}
