package com.zone.weixin4j.interceptor;

import com.zone.weixin4j.exception.WeixinException;
import com.zone.weixin4j.handler.WeixinMessageHandler;
import com.zone.weixin4j.request.WeixinMessage;
import com.zone.weixin4j.request.WeixinRequest;
import com.zone.weixin4j.response.WeixinResponse;

/**
 * 微信消息拦截器
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className WeixinMessageInterceptor
 * @date 2015年5月7日
 * @see MessageInterceptorAdapter
 * @since JDK 1.6
 */
public interface WeixinMessageInterceptor {

    /**
     * 执行handler前
     *
     * @param request 微信请求
     * @param message 微信消息
     * @param handler 消息处理器
     * @return 返回true执行下一个拦截器
     * @throws WeixinException
     */
    boolean preHandle(WeixinRequest request,
                      WeixinMessage message, WeixinMessageHandler handler)
            throws WeixinException;

    /**
     * 执行handler后
     *
     * @param request  微信请求
     * @param response 微信响应
     * @param message  微信消息
     * @param handler  消息处理器
     * @throws WeixinException
     */
    void postHandle(WeixinRequest request,
                    WeixinResponse response, WeixinMessage message,
                    WeixinMessageHandler handler) throws WeixinException;

    /**
     * 全部执行后
     *
     * @param request   微信请求
     * @param message   微信消息
     * @param handler   消息处理器
     * @param exception 执行异常
     * @throws WeixinException
     */
    void afterCompletion(WeixinRequest request,
                         WeixinResponse response, WeixinMessage message,
                         WeixinMessageHandler handler, Exception exception)
            throws WeixinException;

    /**
     * 用于匹配到多个MessageHandler时权重降序排列,数字越大优先级越高
     *
     * @return 权重
     */
    int weight();
}
