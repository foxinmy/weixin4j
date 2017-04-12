package com.zone.weixin4j.interceptor;

import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.handler.WeixinMessageHandler;
import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.response.WeixinResponse;

/**
 * 消息拦截适配
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className MessageInterceptorAdapter
 * @date 2015年5月14日
 * @see
 * @since JDK 1.6
 */
public abstract class MessageInterceptorAdapter implements
        WeixinMessageInterceptor {

    @Override
    public boolean preHandle(
            WeixinRequest request, WeixinMessage message, WeixinMessageHandler handler)
            throws WeixinException {
        return true;
    }

    @Override
    public void postHandle(
            WeixinRequest request, WeixinResponse response, WeixinMessage message,
            WeixinMessageHandler handler) throws WeixinException {
    }

    @Override
    public void afterCompletion(
            WeixinRequest request, WeixinResponse response, WeixinMessage message,
            WeixinMessageHandler handler, Exception exception)
            throws WeixinException {
    }

    @Override
    public int weight() {
        return 0;
    }
}
