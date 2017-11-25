package com.foxinmy.weixin4j.handler;

import java.util.HashSet;
import java.util.Set;

import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;

/**
 * 多个消息类型适配
 *
 * @className MultipleMessageHandlerAdapter
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月12日
 * @since JDK 1.6
 * @see
 */
public abstract class MultipleMessageHandlerAdapter implements WeixinMessageHandler {

    private final Set<Class<? extends WeixinMessage>> messageClasses;

    public MultipleMessageHandlerAdapter(Class<? extends WeixinMessage>... messageClasses) {
        if (messageClasses == null) {
            throw new IllegalArgumentException("messageClasses not be empty");
        }
        this.messageClasses = new HashSet<Class<? extends WeixinMessage>>(
                Math.max((int) (messageClasses.length / .75f) + 1, 16));
        for (Class<? extends WeixinMessage> clazz : messageClasses) {
            this.messageClasses.add(clazz);
        }
    }

    @Override
    public boolean canHandle(WeixinRequest request, WeixinMessage message, Set<String> nodeNames) {
        return message != null && messageClasses.contains(message.getClass()) && canHandle0(request, message);
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
    protected boolean canHandle0(WeixinRequest request, WeixinMessage message) {
        return true;
    }

    @Override
    public int weight() {
        return 1;
    }
}