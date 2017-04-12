package com.zone.weixin4j.service;

import com.zone.weixin4j.dispatcher.WeixinMessageMatcher;
import com.zone.weixin4j.util.AesToken;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Created by Yz on 2017/3/15.
 * WeiXin4j上下文
 */
public interface WeiXin4jContextAware {

    boolean isOpenAlwaysResponse();

    boolean isUseDebugMessageHandler();

    List<AesToken> getAesTokens();

    ApplicationContext getApplicationContext();

    Map<String, AesToken> getAesTokenMap();

    void setAesTokenMap(Map<String, AesToken> aesTokenMap);

    public WeixinMessageMatcher getWeixinMessageMatcher();

    public void setWeixinMessageMatcher(WeixinMessageMatcher weixinMessageMatcher);

}
