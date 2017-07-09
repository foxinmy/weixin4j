package com.foxinmy.weixin4j.mp.component;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.type.URLConsts;
import com.foxinmy.weixin4j.token.PerTicketManager;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 微信公众号token创建(永久刷新令牌)
 *
 * @className WeixinTokenComponentCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年7月5日
 * @since JDK 1.6
 */
public class WeixinTokenComponentCreator extends TokenCreator {

    private final PerTicketManager perTicketManager;
    private final TokenManager componentTokenManager;

    /**
     *
     * @param perTicketManager
     *            第三方套件永久授权码
     * @param componentTokenManager
     *            第三方套件凭证token
     */
    public WeixinTokenComponentCreator(PerTicketManager perTicketManager, TokenManager componentTokenManager) {
        this.perTicketManager = perTicketManager;
        this.componentTokenManager = componentTokenManager;
    }

    @Override
    public String name() {
        return String.format("mp_token_component_%s_%s", perTicketManager.getThirdId(),
                perTicketManager.getAuthAppId());
    }

    @Override
    public String uniqueid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Token create() throws WeixinException {
        JSONObject obj = new JSONObject();
        obj.put("component_appid", perTicketManager.getThirdId());
        obj.put("authorizer_appid", perTicketManager.getAuthAppId());
        obj.put("authorizer_refresh_token", perTicketManager.getAccessTicket());
        WeixinResponse response = weixinExecutor.post(
                String.format(URLConsts.TOKEN_COMPONENT_URL, componentTokenManager.getAccessToken()),
                obj.toJSONString());
        obj = response.getAsJson();
        return new Token(obj.getString("access_token"), obj.getLongValue("expires_in") * 1000l);
    }

}
