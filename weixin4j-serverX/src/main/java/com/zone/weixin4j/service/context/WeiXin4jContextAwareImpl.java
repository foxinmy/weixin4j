package com.zone.weixin4j.service.context;

import com.zone.weixin4j.dispatcher.WeixinMessageMatcher;
import com.zone.weixin4j.service.WeiXin4jContextAware;
import com.zone.weixin4j.socket.WeixinMessageTransfer;
import com.zone.weixin4j.spring.TokenGenerater;
import com.zone.weixin4j.util.AesToken;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yz on 2017/3/15.
 * WeiXin4j上下文
 */
public class WeiXin4jContextAwareImpl implements ApplicationContextAware, WeiXin4jContextAware {

    private static final ThreadLocal<WeixinMessageTransfer> weixinMessageTransfer = new NamedThreadLocal<WeixinMessageTransfer>("WeixinMessageTransfer");

    private ApplicationContext applicationContext;

    private boolean openAlwaysResponse;
    private boolean useDebugMessageHandler;

    private List<AesToken> aesTokens;
    private TokenGenerater tokenGenerater;

    private WeixinMessageMatcher weixinMessageMatcher;

    private Map<String, AesToken> aesTokenMap = new HashMap<String, AesToken>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    public boolean isOpenAlwaysResponse() {
        return openAlwaysResponse;
    }

    public void setOpenAlwaysResponse(boolean openAlwaysResponse) {
        this.openAlwaysResponse = openAlwaysResponse;
    }

    public boolean isUseDebugMessageHandler() {
        return useDebugMessageHandler;
    }

    public void setUseDebugMessageHandler(boolean useDebugMessageHandler) {
        this.useDebugMessageHandler = useDebugMessageHandler;
    }

    public List<AesToken> getAesTokens() {
        return aesTokens;
    }

    public void setAesTokens(List<AesToken> aesTokens) {
        this.aesTokens = aesTokens;
    }

    public void setTokenGenerater(TokenGenerater tokenGenerater) {
        this.tokenGenerater = tokenGenerater;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void init() {
        this.aesTokens = tokenGenerater.getAesTokens();
        for(AesToken aesToken : this.aesTokens){
            this.aesTokenMap.put(StringUtils.isEmpty(aesToken.getWeixinId()) ? "" : aesToken.getWeixinId(), aesToken);
        }
    }

    public void destroy() {
        this.applicationContext = null;
    }

    public static ThreadLocal<WeixinMessageTransfer> getWeixinMessageTransfer() {
        return weixinMessageTransfer;
    }

    public Map<String, AesToken> getAesTokenMap() {
        return aesTokenMap;
    }

    public void setAesTokenMap(Map<String, AesToken> aesTokenMap) {
        this.aesTokenMap = aesTokenMap;
    }

    public WeixinMessageMatcher getWeixinMessageMatcher() {
        return weixinMessageMatcher;
    }

    public void setWeixinMessageMatcher(WeixinMessageMatcher weixinMessageMatcher) {
        this.weixinMessageMatcher = weixinMessageMatcher;
    }
}
