package com.foxinmy.weixin4j.handler;

import java.util.Set;

import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

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
    public boolean canHandle(WeixinRequest request, WeixinMessage message, Set<String> nodeNames) {
        return true;
    }

    @Override
    public WeixinResponse doHandle(WeixinRequest request, WeixinMessage message) {
        String content = message == null
                ? request.getOriginalContent().replaceAll("\\!\\[CDATA\\[", "").replaceAll("\\]\\]", "")
                : message.toString();
        return new TextResponse(content);
    }

    @Override
    public int weight() {
        return 0;
    }
}
