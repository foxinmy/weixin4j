package com.foxinmy.weixin4j.example.server.handler;

import org.springframework.stereotype.Component;

import com.foxinmy.weixin4j.handler.MessageHandlerAdapter;
import com.foxinmy.weixin4j.mp.event.ScribeEventMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 处理关注消息
 *
 * @className SubscribeMessageHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月3日
 * @since JDK 1.6
 */
@Component
public class SubscribeMessageHandler extends MessageHandlerAdapter<ScribeEventMessage> {

    @Override
    public WeixinResponse doHandle0(ScribeEventMessage message) {
        return new TextResponse("欢迎关注～");
    }
}
