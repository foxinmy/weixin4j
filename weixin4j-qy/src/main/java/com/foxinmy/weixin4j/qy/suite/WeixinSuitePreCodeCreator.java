package com.foxinmy.weixin4j.qy.suite;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 微信企业号应用套件预授权码创建
 *
 * @className WeixinSuitePreCodeCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月17日
 * @since JDK 1.6
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.8E.B7.E5.8F.96.E9.A2.84.E6.8E.88.E6.9D.83.E7.A0.81">
 *      获取应用套件预授权码</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinSuitePreCodeCreator extends TokenCreator {

	private final TokenManager suiteTokenManager;
	private final String suiteId;

	/**
	 *
	 * @param suiteTokenManager
	 *            应用套件的token
	 * @param suiteId
	 *            应用套件ID
	 */
	public WeixinSuitePreCodeCreator(TokenManager suiteTokenManager,
			String suiteId) {
		this.suiteTokenManager = suiteTokenManager;
		this.suiteId = suiteId;
	}

	@Override
	public String key0() {
		return String.format("qy_suite_precode_%s", suiteId);
	}

	@Override
	public Token create() throws WeixinException {
		WeixinResponse response = weixinExecutor.post(
				String.format(URLConsts.SUITE_PRE_CODE_URL,
						suiteTokenManager.getAccessToken()),
				String.format("{\"suite_id\":\"%s\"}", suiteId));
		JSONObject result = response.getAsJson();
		return new Token(result.getString("pre_auth_code"),
				result.getLongValue("expires_in") * 1000l);
	}
}
