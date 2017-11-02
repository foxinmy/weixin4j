package com.foxinmy.weixin4j.qy.suite;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TicketManager;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 微信企业号应用套件凭证创建
 *
 * @className WeixinSuiteTokenCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月17日
 * @since JDK 1.6
 * @see <a href= "http://work.weixin.qq.com/api/doc#10975/获取应用套件凭证">获取应用套件凭证</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinSuiteTokenCreator extends TokenCreator {

	private final TicketManager ticketManager;

	/**
	 *
	 * @param ticketManager
	 *            套件ticket存取
	 */
	public WeixinSuiteTokenCreator(TicketManager ticketManager) {
		this.ticketManager = ticketManager;
	}

	@Override
	public String name() {
		return "qy_suite_token";
	}

	@Override
	public String uniqueid() {
		return ticketManager.getThirdId();
	}

	@Override
	public Token create() throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("suite_id", ticketManager.getThirdId());
		obj.put("suite_secret", ticketManager.getThirdSecret());
		obj.put("suite_ticket", ticketManager.getAccessTicket());
		WeixinResponse response = weixinExecutor.post(
				URLConsts.SUITE_TOKEN_URL, obj.toJSONString());
		obj = response.getAsJson();
		return new Token(obj.getString("suite_access_token"),
				obj.getLongValue("expires_in") * 1000l);
	}
}
