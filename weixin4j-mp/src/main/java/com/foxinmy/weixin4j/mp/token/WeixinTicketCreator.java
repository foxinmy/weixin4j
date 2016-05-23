package com.foxinmy.weixin4j.mp.token;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.type.URLConsts;
import com.foxinmy.weixin4j.token.AbstractTokenCreator;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.type.TicketType;

/**
 * 微信公众平台TICKET创建(包括jsticket、其它JSSDK所需的ticket的创建
 * 
 * @className WeixinJSTicketCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN">
 *      JS TICKET</a>
 */
public class WeixinTicketCreator extends AbstractTokenCreator {

	private final String appid;
	private final TicketType ticketType;
	private final TokenHolder weixinTokenHolder;

	/**
	 * jssdk
	 * 
	 * @param appid
	 *            公众号的appid
	 * @param ticketType
	 *            票据类型
	 * @param weixinTokenHolder
	 *            <font color="red">公众平台的access_token</font>
	 */
	public WeixinTicketCreator(String appid, TicketType ticketType, TokenHolder weixinTokenHolder) {
		this.appid = appid;
		this.ticketType = ticketType;
		this.weixinTokenHolder = weixinTokenHolder;
	}

	@Override
	public String getCacheKey0() {
		return String.format("mp_ticket_%s_%s", ticketType.name(), appid);
	}

	@Override
	public Token createToken() throws WeixinException {
		WeixinResponse response = weixinExecutor.get(
				String.format(URLConsts.JS_TICKET_URL, weixinTokenHolder.getToken().getAccessToken(), ticketType.name()));
		JSONObject result = response.getAsJson();
		Token token = new Token(result.getString("ticket"));
		token.setExpiresIn(result.getIntValue("expires_in"));
		token.setOriginalResult(response.getAsString());
		return token;
	}
}
