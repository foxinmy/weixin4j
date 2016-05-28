package com.foxinmy.weixin4j.qy.suite;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 微信企业号应用套件凭证创建
 *
 * @className WeixinSuiteTokenCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月17日
 * @since JDK 1.6
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.8E.B7.E5.8F.96.E5.BA.94.E7.94.A8.E5.A5.97.E4.BB.B6.E4.BB.A4.E7.89.8C">获取应用套件凭证</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinSuiteTokenCreator extends TokenCreator {

	private final SuiteTicketManager ticketManager;

	/**
	 *
	 * @param ticketManager
	 *            套件ticket存取
	 */
	public WeixinSuiteTokenCreator(SuiteTicketManager ticketManager) {
		this.ticketManager = ticketManager;
	}

	@Override
	public String key0() {
		return String.format("qy_suite_token_%s", ticketManager.getSuiteId());
	}

	@Override
	public Token create() throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("suite_id", ticketManager.getSuiteId());
		obj.put("suite_secret", ticketManager.getSuiteSecret());
		obj.put("suite_ticket", ticketManager.getTicket());
		WeixinResponse response = weixinExecutor.post(
				URLConsts.SUITE_TOKEN_URL, obj.toJSONString());
		obj = response.getAsJson();
		return new Token(obj.getString("suite_access_token"),
				obj.getLongValue("expires_in") * 1000l);
	}
}
