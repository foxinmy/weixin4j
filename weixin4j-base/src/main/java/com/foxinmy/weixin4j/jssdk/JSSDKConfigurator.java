package com.foxinmy.weixin4j.jssdk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * JSSDK配置类
 *
 * @className JSSDKConfigurator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月23日
 * @since JDK 1.6
 * @see
 */
public class JSSDKConfigurator {
    private final TokenManager ticketTokenManager;
    private JSONObject config;
    private Set<JSSDKAPI> apis;

    /**
     * ticket保存类 可调用WeixinProxy#getTicketManager获取
     *
     * @param ticketTokenManager
     */
    public JSSDKConfigurator(TokenManager ticketTokenManager) {
        this.ticketTokenManager = ticketTokenManager;
        this.config = new JSONObject();
        this.apis = new HashSet<JSSDKAPI>();
    }

    /**
     * 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，
     * 仅在pc端时才会打印。
     *
     * @return
     */
    public JSSDKConfigurator debugMode() {
        config.put("debug", true);
        return this;
    }

    /**
     * 需要使用的JS接口列表
     *
     * @see JSSDKAPI
     * @param apis
     * @return
     */
    public JSSDKConfigurator apis(JSSDKAPI... apis) {
        for (JSSDKAPI api : apis) {
            this.apis.add(api);
        }
        return this;
    }

    /**
     * 需要使用的JS接口列表
     *
     * @see JSSDKAPI
     * @param apis
     * @return
     */
    public JSSDKConfigurator apis(JSSDKAPI[]... apis) {
        for (JSSDKAPI[] api : apis) {
            apis(api);
        }
        return this;
    }

    /**
     * 生成config配置JSON串
     *
     * @param url
     *            当前网页的URL，不包含#及其后面部分
     * @return jssdk配置JSON字符串
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN">公众号JSSDK</a>
     * @see <a href=
     *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BE%AE%E4%BF%A1JS-SDK%E6%8E%A5%E5%8F%A3">企业号JSSDK</a>
     * @throws WeixinException
     */
    public String toJSONConfig(String url) throws WeixinException {
        if (apis.isEmpty()) {
            throw new WeixinException("jsapilist not be empty");
        }
        Map<String, String> signMap = new HashMap<String, String>();
        String timestamp = DateUtil.timestamp2string();
        String noncestr = RandomUtil.generateString(24);
        signMap.put("timestamp", timestamp);
        signMap.put("noncestr", noncestr);
        signMap.put("jsapi_ticket", this.ticketTokenManager.getAccessToken());
        signMap.put("url", url);
        String sign = DigestUtil.SHA1(MapUtil.toJoinString(signMap, false, false));
        config.put("appId", ticketTokenManager.getWeixinId());
        if (StringUtil.isBlank(config.getString("debug"))) {
            config.put("debug", false);
        }
        config.put("timestamp", timestamp);
        config.put("nonceStr", noncestr);
        config.put("signature", sign);
        config.put("jsApiList", apis.toArray());
        return config.toJSONString();
    }
}
