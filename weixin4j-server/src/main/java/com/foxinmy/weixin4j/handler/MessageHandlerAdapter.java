package com.foxinmy.weixin4j.handler;

import java.util.Set;

import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;
import com.foxinmy.weixin4j.util.ClassUtil;

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
			Set<String> nodeNames) {
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
	 * @return true则执行doHandler0 @
	 */
	protected boolean canHandle0(WeixinRequest request, M message) {
		return true;
	}

	@Override
	public WeixinResponse doHandle(WeixinRequest request, WeixinMessage message) {
		return doHandle0((M) message);
	}

	/**
	 * 处理请求
	 *
	 * @param message
	 *            微信消息
	 * @return
	 */
	protected abstract WeixinResponse doHandle0(M message);

	/**
	 * 缺省值为1,存在多个匹配到的MessageHandler则比较weight大小
	 */
	@Override
	public int weight() {
		return 1;
	}
}
