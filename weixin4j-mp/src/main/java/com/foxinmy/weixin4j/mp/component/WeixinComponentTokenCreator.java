package com.foxinmy.weixin4j.mp.component;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.type.URLConsts;
import com.foxinmy.weixin4j.token.TicketManager;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 微信开放平台应用组件凭证创建
 *
 * @className WeixinComponentTokenCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月5日
 * @since JDK 1.6
 */
public class WeixinComponentTokenCreator extends TokenCreator {
    private final TicketManager ticketManager;

    /**
     *
     * @param ticketManager
     *            组件ticket存取
     */
    public WeixinComponentTokenCreator(TicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }

    @Override
    public String name() {
        return "mp_component_token";
    }

    @Override
    public String uniqueid() {
        return ticketManager.getThirdId();
    }

    @Override
    public Token create() throws WeixinException {
        JSONObject obj = new JSONObject();
        obj.put("component_appid", ticketManager.getThirdId());
        obj.put("component_appsecret", ticketManager.getThirdSecret());
        obj.put("component_verify_ticket", ticketManager.getAccessTicket());
        WeixinResponse response = weixinExecutor.post(URLConsts.COMPONENT_TOKEN_URL, obj.toJSONString());
        obj = response.getAsJson();
        return new Token(obj.getString("component_access_token"), obj.getLongValue("expires_in") * 1000l);
    }
}
