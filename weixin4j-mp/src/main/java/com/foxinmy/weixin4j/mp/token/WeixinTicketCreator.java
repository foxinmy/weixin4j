package com.foxinmy.weixin4j.mp.token;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.type.TicketType;

/**
 * 微信公众平台TICKET创建(包括jsticket、其它JSSDK所需的ticket的创建
 *
 * @className WeixinTicketCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN">
 *      JS TICKET</a>
 */
public class WeixinTicketCreator extends TokenCreator {

	private final String appid;
	private final TicketType ticketType;
	private final TokenManager weixinTokenManager;

	/**
	 * jssdk
	 *
	 * @param appid
	 *            公众号的appid
	 * @param ticketType
	 *            票据类型
	 * @param weixinTokenManager
	 *            <font color="red">公众平台的access_token</font>
	 */
	public WeixinTicketCreator(String appid, TicketType ticketType,
			TokenManager weixinTokenManager) {
		this.appid = appid;
		this.ticketType = ticketType;
		this.weixinTokenManager = weixinTokenManager;
	}

	@Override
	public String key0() {
		return String.format("mp_ticket_%s_%s", ticketType.name(), appid);
	}

	@Override
	public Token create() throws WeixinException {
		WeixinResponse response = weixinExecutor.get(String.format(
				URLConsts.JS_TICKET_URL, weixinTokenManager.getAccessToken(),
				ticketType.name()));
		JSONObject result = response.getAsJson();
		return new Token(result.getString("ticket"),
				result.getLongValue("expires_in") * 1000l);
	}
}
