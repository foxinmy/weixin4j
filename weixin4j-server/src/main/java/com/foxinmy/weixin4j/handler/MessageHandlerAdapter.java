package com.foxinmy.weixin4j.handler;

import java.util.Set;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 消息处理的适配,主要对微信消息进行泛型转换
 * 
 * @className MessageHandlerAdapter
 * @author jy
 * @date 2015年5月17日
 * @since JDK 1.7
 * @see
 */
@SuppressWarnings("unchecked")
public abstract class MessageHandlerAdapter<M extends WeixinMessage> implements
		WeixinMessageHandler {

	@Override
	public boolean canHandle(WeixinRequest request, Object message,
			Set<String> nodeNames) throws WeixinException {
		return canHandle0(request, (M) message);
	}

	/**
	 * 能否处理请求
	 * 
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @return true则执行doHandler
	 * @throws WeixinException
	 */
	public boolean canHandle0(WeixinRequest request, M message)
			throws WeixinException {
		return true;
	}

	@Override
	public WeixinResponse doHandle(WeixinRequest request, Object message,
			Set<String> nodeNames) throws WeixinException {
		return doHandle0(request, (M) message);
	}

	/**
	 * 处理请求
	 * 
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @return
	 */
	public abstract WeixinResponse doHandle0(WeixinRequest request, M message)
			throws WeixinException;

}
