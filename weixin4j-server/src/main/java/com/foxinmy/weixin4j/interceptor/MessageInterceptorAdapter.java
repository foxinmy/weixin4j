package com.foxinmy.weixin4j.interceptor;

import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.WeixinResponse;

import io.netty.channel.ChannelHandlerContext;

/**
 * 消息拦截适配
 *
 * @className MessageInterceptorAdapter
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年5月14日
 * @since JDK 1.6
 * @see
 */
public abstract class MessageInterceptorAdapter implements WeixinMessageInterceptor {

    @Override
    public boolean preHandle(ChannelHandlerContext context, WeixinRequest request, WeixinMessage message,
            WeixinMessageHandler handler) {
        return true;
    }

    @Override
    public void postHandle(ChannelHandlerContext context, WeixinRequest request, WeixinResponse response,
            WeixinMessage message, WeixinMessageHandler handler) {
    }

    @Override
    public void afterCompletion(ChannelHandlerContext context, WeixinRequest request, WeixinResponse response,
            WeixinMessage message, WeixinMessageHandler handler, Exception exception) {
    }

    @Override
    public int weight() {
        return 0;
    }
}
