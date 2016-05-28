package com.foxinmy.weixin4j.qy.token;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.type.TicketType;

/**
 * 微信企业号TICKET创建(包括jsticket、其它JSSDK所需的ticket的创建
 *
 * @className WeixinTicketCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月25日
 * @since JDK 1.6 <a href=
 *        "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BE%AE%E4%BF%A1JS-SDK%E6%8E%A5%E5%8F%A3#.E9.99.84.E5.BD.951-JS-SDK.E4.BD.BF.E7.94.A8.E6.9D.83.E9.99.90.E7.AD.BE.E5.90.8D.E7.AE.97.E6.B3.95"
 *        >JSTICKET</a>
 */
public class WeixinTicketCreator extends TokenCreator {

	private final String corpid;
	private final TicketType ticketType;
	private final TokenManager weixinTokenManager;

	/**
	 * @param corpid
	 *            企业号ID
	 * @param ticketType
	 *            票据类型
	 * @param weixinTokenManager
	 *            <font color="red">企业号的access_token</font>
	 */
	public WeixinTicketCreator(String corpid, TicketType ticketType,
			TokenManager weixinTokenManager) {
		this.corpid = corpid;
		this.ticketType = ticketType;
		this.weixinTokenManager = weixinTokenManager;
	}

	@Override
	public String key0() {
		return String.format("qy_ticket_%s_%s", ticketType.name(), corpid);
	}

	@Override
	public Token create() throws WeixinException {
		WeixinResponse response = null;
		if (ticketType == TicketType.jsapi) {
			response = weixinExecutor.get(String.format(
					URLConsts.JS_TICKET_URL, weixinTokenManager.getCache()
							.getAccessToken()));
		} else {
			response = weixinExecutor.get(String.format(
					URLConsts.SUITE_TICKET_URL, weixinTokenManager.getCache()
							.getAccessToken(), ticketType.name()));
		}
		JSONObject result = response.getAsJson();
		return new Token(result.getString("ticket"),
				result.getLong("expires_in") * 1000l);
	}
}
