package com.foxinmy.weixin4j.qy.suite;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.PerTicketManager;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 微信企业号token创建(永久授权码)
 *
 * @className WeixinTokenSuiteCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月17日
 * @since JDK 1.6
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.8E.B7.E5.8F.96.E4.BC.81.E4.B8.9A.E5.8F.B7access_token">
 *      获取企业号access_token</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinTokenSuiteCreator extends TokenCreator {

	private final PerTicketManager perTicketManager;
	private final TokenManager suiteTokenManager;

	/**
	 *
	 * @param perTicketManager
	 *            第三方套件永久授权码
	 * @param suiteTokenManager
	 *            第三方套件凭证token
	 */
	public WeixinTokenSuiteCreator(PerTicketManager perTicketManager,
			TokenManager suiteTokenManager) {
		this.perTicketManager = perTicketManager;
		this.suiteTokenManager = suiteTokenManager;
	}

	@Override
	public String key0() {
		return String.format("qy_token_suite_%s_%s",
				perTicketManager.getThirdId(), perTicketManager.getAuthAppId());
	}

	@Override
	public Token create() throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("suite_id", perTicketManager.getThirdId());
		obj.put("auth_corpid", perTicketManager.getAuthAppId());
		obj.put("permanent_code", perTicketManager.getAccessTicket());
		WeixinResponse response = weixinExecutor.post(
				String.format(URLConsts.TOKEN_SUITE_URL,
						suiteTokenManager.getAccessToken()), obj.toJSONString());
		obj = response.getAsJson();
		return new Token(obj.getString("access_token"),
				obj.getLongValue("expires_in") * 1000l);
	}
}
