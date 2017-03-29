package com.zone.weixin4j.dispatcher;

import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.handler.WeixinMessageHandler;
import com.zone.weixin4j.interceptor.WeixinMessageInterceptor;
import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.response.WeixinResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 微信消息的处理执行
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className MessageHandlerExecutor
 * @date 2015年5月7日
 * @see com.zone.weixin4j.handler.WeixinMessageHandler
 * @see com.zone.weixin4j.interceptor.WeixinMessageInterceptor
 * @since JDK 1.6
 */
public class MessageHandlerExecutor {

    private final Log logger = LogFactory.getLog(getClass());
    /**
     * 消息处理器
     */
    private final WeixinMessageHandler messageHandler;

    /**
     * 消息拦截器
     */
    private final WeixinMessageInterceptor[] messageInterceptors;

    private int interceptorIndex = -1;

    public MessageHandlerExecutor(WeixinMessageHandler messageHandler, WeixinMessageInterceptor[] messageInterceptors) {
        this.messageHandler = messageHandler;
        this.messageInterceptors = messageInterceptors;
    }

    public WeixinMessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * 执行预拦截动作
     *
     * @param request 微信请求信息
     * @param message 微信消息
     * @return true则继续执行往下执行
     * @throws WeixinException
     */
    public boolean applyPreHandle(WeixinRequest request, WeixinMessage message)
            throws WeixinException {
        if (messageInterceptors != null) {
            for (int i = 0; i < messageInterceptors.length; i++) {
                WeixinMessageInterceptor interceptor = messageInterceptors[i];
                if (!interceptor.preHandle(request, message, messageHandler)) {
                    triggerAfterCompletion(request, null, message, null);
                    return false;
                }
                this.interceptorIndex = i;
            }
        }
        return true;
    }

    /**
     * MessageHandler处理玩请求后的动作
     *
     * @param request  微信请求
     * @param response 处理后的响应
     * @param message  微信消息
     * @throws WeixinException
     */
    public void applyPostHandle(WeixinRequest request, WeixinResponse response,
                                WeixinMessage message) throws WeixinException {
        if (messageInterceptors == null) {
            return;
        }
        for (int i = messageInterceptors.length - 1; i >= 0; i--) {
            WeixinMessageInterceptor interceptor = messageInterceptors[i];
            interceptor.postHandle(request, response, message, messageHandler);
        }
    }

    /**
     * 全部执行完毕后触发
     *
     * @param request   微信请求
     * @param response  微信响应 可能为空
     * @param message   微信消息
     * @param exception 处理时可能的异常
     * @throws WeixinException
     */
    public void triggerAfterCompletion(WeixinRequest request,
                                       WeixinResponse response, WeixinMessage message, Exception exception)
            throws WeixinException {
        if (messageInterceptors == null) {
            return;
        }
        for (int i = this.interceptorIndex; i >= 0; i--) {
            WeixinMessageInterceptor interceptor = messageInterceptors[i];
            try {
                interceptor.afterCompletion(request, response, message, messageHandler, exception);
            } catch (WeixinException e) {
                logger.error("MessageInterceptor.afterCompletion threw exception", e);
            }
        }
    }
}
