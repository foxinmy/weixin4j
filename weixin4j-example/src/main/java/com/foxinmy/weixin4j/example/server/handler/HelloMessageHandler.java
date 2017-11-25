package com.foxinmy.weixin4j.example.server.handler;

import org.springframework.stereotype.Component;

import com.foxinmy.weixin4j.message.TextMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 输入 hello 回复 world
 *
 * @className HelloMessageHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月27日
 * @since JDK 1.6
 */
@Component
public class HelloMessageHandler extends TextMessageHandler {

    @Override
    public boolean canHandle0(WeixinRequest request, TextMessage message) {
        /**
         * 用户输入hello时
         */
        return message.getContent().equalsIgnoreCase("hello");
    }

    @Override
    public WeixinResponse doHandle0(TextMessage message) {
        /**
         * 返回用户「world」文本
         */
        return new TextResponse("world");
    }

    /**
     * 因为HelloMessageHandler和TextMessageHandler都会匹配到文本消息
     * 所以这里需要提高下权重(大于TextMessageHandler就行了) > TextMessageHandler
     */
    @Override
    public int weight() {
        return super.weight() + 1;
    }
}
