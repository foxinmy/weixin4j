package com.zone.weixin4j.handler;

import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.response.TextResponse;
import com.zone.weixin4j.response.WeixinResponse;

import java.util.Set;

/**
 * 调试消息处理器
 * 
 * @className DebugMessageHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月17日
 * @since JDK 1.6
 * @see
 */
public class DebugMessageHandler implements WeixinMessageHandler {

	public static final DebugMessageHandler global = new DebugMessageHandler();

	private DebugMessageHandler() {

	}

	@Override
	public boolean canHandle(WeixinRequest request, WeixinMessage message,
			Set<String> nodeNames) throws WeixinException {
		return true;
	}

	@Override
	public WeixinResponse doHandle(WeixinRequest request, WeixinMessage message,
			Set<String> nodeNames) throws WeixinException {
		String content = message == null ? request.getOriginalContent()
				.replaceAll("\\!\\[CDATA\\[", "").replaceAll("\\]\\]", "")
				: message.toString();
		return new TextResponse(content);
	}

	@Override
	public int weight() {
		return 0;
	}
}
