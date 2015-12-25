package com.foxinmy.weixin4j.qy.token;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.TicketType;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 微信企业号TICKET创建(包括jsticket、其它JSSDK所需的ticket的创建
 * 
 * @className WeixinTicketCreator
 * @author jy
 * @date 2015年12月25日
 * @since JDK 1.6
 * 
 */
public class WeixinTicketCreator implements TokenCreator {

	private final String corpid;
	private final TicketType ticketType;
	private final TokenHolder weixinTokenHolder;
	private final WeixinRequestExecutor weixinExecutor;

	/**
	 * @param corpid
	 *            企业号ID
	 * @param ticketType
	 *            票据类型
	 * @param weixinTokenHolder
	 *            <font color="red">企业号的的access_token</font>
	 */
	public WeixinTicketCreator(String corpid, TicketType ticketType,
			TokenHolder weixinTokenHolder) {
		this.corpid = corpid;
		this.ticketType = ticketType;
		this.weixinTokenHolder = weixinTokenHolder;
		this.weixinExecutor = new WeixinRequestExecutor();
	}

	@Override
	public String getCacheKey() {
		return String.format("wx_qy_ticket_%s_%s", corpid, ticketType.name());
	}

	@Override
	public Token createToken() throws WeixinException {
		WeixinResponse response = null;
		if (ticketType == TicketType.jsticket) {
			response = weixinExecutor.get(String.format(
					URLConsts.JS_TICKET_URL, weixinTokenHolder.getToken()
							.getAccessToken()));
		} else {
			response = weixinExecutor.get(String.format(URLConsts.TICKET_URL,
					weixinTokenHolder.getToken().getAccessToken(),
					ticketType.name()));
		}
		JSONObject result = response.getAsJson();
		Token token = new Token(result.getString("ticket"));
		token.setExpiresIn(result.getIntValue("expires_in"));
		token.setCreateTime(System.currentTimeMillis());
		token.setOriginalResult(response.getAsString());
		return token;
	}
}
