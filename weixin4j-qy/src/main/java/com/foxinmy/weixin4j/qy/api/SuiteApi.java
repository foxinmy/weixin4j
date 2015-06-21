package com.foxinmy.weixin4j.qy.api;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.qy.suite.SuiteTicketProcessor;
import com.foxinmy.weixin4j.qy.suite.WeixinSuitePreCodeCreator;
import com.foxinmy.weixin4j.qy.suite.WeixinSuiteTokenCreator;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.token.TokenStorager;

/**
 * 第三方应用套件
 * 
 * @className SuiteApi
 * @author jy
 * @date 2015年6月17日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%88%E6%9D%83">第三方应用授权</a>
 */
public class SuiteApi extends QyApi {

	/**
	 * 应用套件token
	 */
	private final TokenHolder suiteTokenHolder;
	private final TokenHolder suiteTicketHolder;

	public SuiteApi() throws WeixinException {
		this(DEFAULT_WEIXIN_ACCOUNT.getSuiteId(), DEFAULT_WEIXIN_ACCOUNT
				.getSuiteSecret());
	}

	public SuiteApi(String suiteId, String suiteSecret) throws WeixinException {
		this(suiteId, suiteSecret, DEFAULT_TICKET_PROCESSOR,
				DEFAULT_TOKEN_STORAGER);
	}

	/**
	 * 
	 * @param suiteId
	 *            应用ID
	 * @param suiteSecret
	 *            应用secret
	 * @param ticketReader
	 *            应用ticket读取器
	 * @param tokenStorager
	 *            应用token存储器
	 * @throws WeixinException
	 */
	public SuiteApi(String suiteId, String suiteSecret,
			SuiteTicketProcessor ticketReader, TokenStorager tokenStorager)
			throws WeixinException {
		this.suiteTokenHolder = new TokenHolder(new WeixinSuiteTokenCreator(
				suiteId, suiteSecret, ticketReader), tokenStorager);
		this.suiteTicketHolder = new TokenHolder(new WeixinSuitePreCodeCreator(
				suiteTokenHolder, suiteId), tokenStorager);
	}

	/**
	 * 应用套件token
	 * 
	 * @return
	 */
	public TokenHolder getTokenHolder() {
		return this.suiteTokenHolder;
	}

	/**
	 * 应用套件ticket
	 * 
	 * @return
	 */
	public TokenHolder getTicketHolder() {
		return this.suiteTicketHolder;
	}

	/**
	 * 设置套件授权配置:如果需要对某次授权进行配置，则调用本接口，目前仅可以设置哪些应用可以授权，不调用则默认允许所有应用进行授权。
	 * 
	 * @param appids
	 *            允许进行授权的应用id，如1、2、3
	 * @return 处理结果
	 * @throws WeixinException
	 *             <a href=
	 *             "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E#.E8.AE.BE.E7.BD.AE.E6.8E.88.E6.9D.83.E9.85.8D.E7.BD.AE"
	 *             >设置套件授权配置</a>
	 */
	public JsonResult setSuiteSession(int... appids) throws WeixinException {
		String suite_set_session_uri = getRequestUri("suite_set_session_uri");
		JSONObject para = new JSONObject();
		para.put("pre_auth_code", suiteTicketHolder.getAccessToken());
		para.put("session_info", appids);
		WeixinResponse response = weixinClient
				.post(String.format(suite_set_session_uri,
						suiteTokenHolder.getAccessToken()), para.toJSONString());
		return response.getAsJsonResult();
	}
}
