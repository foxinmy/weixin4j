package com.foxinmy.weixin4j.qy.suite;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenStorager;

/**
 * 应用套件ticket的存取
 * 
 * @className SuiteTicketHolder
 * @author jy
 * @date 2015年6月22日
 * @since JDK 1.7
 * @see
 */
public class SuiteTicketHolder {

	private final String suiteId;
	private final String suiteSecret;
	private final TokenStorager tokenStorager;

	public SuiteTicketHolder(String suiteId, String suiteSecret,
			TokenStorager tokenStorager) {
		this.suiteId = suiteId;
		this.suiteSecret = suiteSecret;
		this.tokenStorager = tokenStorager;
	}

	/**
	 * 获取ticket
	 * 
	 * @return
	 * @throws WeixinException
	 */
	public String getTicket() throws WeixinException {
		return tokenStorager.lookup(getCacheKey()).getAccessToken();
	}

	/**
	 * 获取ticket的key
	 * 
	 * @return
	 */
	public String getCacheKey() {
		return String.format("qy_suite_ticket_%s", suiteId);
	}

	/**
	 * 缓存ticket
	 * 
	 * @param ticket
	 * @throws WeixinException
	 */
	public void cachingTicket(String ticket) throws WeixinException {
		Token token = new Token(ticket);
		token.setExpiresIn(-1);
		tokenStorager.caching(getCacheKey(), token);
	}

	public String getSuiteId() {
		return this.suiteId;
	}

	public String getSuiteSecret() {
		return this.suiteSecret;
	}

	public TokenStorager getTokenStorager() {
		return this.tokenStorager;
	}
}
