package com.foxinmy.weixin4j.handler;

import java.util.Set;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.util.ClassUtil;

/**
 * 消息适配器
 * 
 * @className MessageHandlerAdapter
 * @author jy
 * @date 2015年5月17日
 * @since JDK 1.6
 * @see
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
	 * 存在多个匹配到的MessageHandler则比较
	 */
	@Override
	public int weight() {
		return 1;
	}
}
