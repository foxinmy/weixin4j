package com.foxinmy.weixin4j.mp.token;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 微信公众平台TOKEN创建者
 *
 * @className WeixinTokenCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN">微信公众平台获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinTokenCreator extends TokenCreator {

    private final String appid;
    private final String secret;

    /**
     *
     * @param appid
     *            公众号ID
     * @param secret
     *            公众号secret
     */
    public WeixinTokenCreator(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
    }

    @Override
    public String name() {
        return "mp_token";
    }

    @Override
    public String uniqueid() {
        return appid;
    }

    @Override
    public Token create() throws WeixinException {
        String tokenUrl = String.format(URLConsts.ASSESS_TOKEN_URL, appid, secret);
        WeixinResponse response = weixinExecutor.get(tokenUrl);
        JSONObject result = response.getAsJson();
        return new Token(result.getString("access_token"), result.getLongValue("expires_in") * 1000l);
    }
}
