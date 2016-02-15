package com.foxinmy.weixin4j.qy.token;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.type.TicketType;

/**
 * 微信企业号TICKET创建(包括jsticket、其它JSSDK所需的ticket的创建
 * 
 * @className WeixinTicketCreator
 * @author jy
 * @date 2015年12月25日
 * @since JDK 1.6 <a href=
 *        "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BE%AE%E4%BF%A1JS-SDK%E6%8E%A5%E5%8F%A3#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95"
 *        >JSTICKET</a>
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
		return String.format("weixin4j_qy_ticket_%s_%s", corpid, ticketType.name());
	}

	@Override
	public Token createToken() throws WeixinException {
		WeixinResponse response = null;
		if (ticketType == TicketType.jsapi) {
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
