package com.foxinmy.weixin4j.example.server.handler;

import java.util.Set;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.TextResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 自定义处理消息
 *
 * @className CustomMessageHandler
 * @author jinyu(foxinmy@gmail.com)
 * @date 2017年1月19日
 * @since JDK 1.6
 * @see
 */
public class CustomMessageHandler implements WeixinMessageHandler {

    @Override
    public boolean canHandle(WeixinRequest request, WeixinMessage message, Set<String> nodeNames) {
        // 消息来源某个用户
        return message.getFromUserName().equals("xxx");
    }

    @Override
    public WeixinResponse doHandle(WeixinRequest request, WeixinMessage messager) {
        return new TextResponse("是你，是你，还是你。");
    }

    @Override
    public int weight() {
        return 0;
    }
}
