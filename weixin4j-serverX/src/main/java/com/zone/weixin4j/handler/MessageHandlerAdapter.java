package com.zone.weixin4j.handler;

import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.response.WeixinResponse;
import com.zone.weixin4j.util.ClassUtil;

import java.util.Set;

/**
 * 消息适配器:对于特定的消息类型进行适配,如text文本、voice语音消息
 * 
 * @className MessageHandlerAdapter
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月17日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.request.WeixinMessage
 */
@SuppressWarnings("unchecked")
public abstract class MessageHandlerAdapter<M extends WeixinMessage> implements
		WeixinMessageHandler {

	@Override
	public boolean canHandle(WeixinRequest request, WeixinMessage message,
			Set<String> nodeNames) throws WeixinException {
		return message != null
				&& message.getClass() == ClassUtil.getGenericType(getClass())
				&& canHandle0(request, (M) message);
	}

	/**
	 * 能否处理请求
	 * 
	 * @param request
	 *            微信请求
	 * @param message
	 *            微信消息
	 * @return true则执行doHandler0
	 * @throws WeixinException
	 */
	public boolean canHandle0(WeixinRequest request, M message)
			throws WeixinException {
		return true;
	}

	@Override
	public WeixinResponse doHandle(WeixinRequest request,
			WeixinMessage message, Set<String> nodeNames)
			throws WeixinException {
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

	/**
	 * 缺省值为1,存在多个匹配到的MessageHandler则比较weight大小
	 */
	@Override
	public int weight() {
		return 1;
	}
}
