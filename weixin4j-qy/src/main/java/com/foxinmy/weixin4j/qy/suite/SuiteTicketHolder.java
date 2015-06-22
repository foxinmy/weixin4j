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

	public final TokenStorager tokenStorager;

	public SuiteTicketHolder(TokenStorager tokenStorager) {
		this.tokenStorager = tokenStorager;
	}

	/**
	 * 查找ticket
	 * 
	 * @param suiteId
	 * @return
	 * @throws WeixinException
	 */
	public String lookup(String suiteId) throws WeixinException {
		return tokenStorager.lookup(getCacheKey(suiteId)).getAccessToken();
	}

	/**
	 * 获取ticket的key
	 * 
	 * @param suiteId
	 * @return
	 */
	private String getCacheKey(String suiteId) {
		return String.format("qy_suite_ticket_%s", suiteId);
	}

	/**
	 * 缓存ticket
	 * 
	 * @param suiteTicket
	 * @throws WeixinException
	 */
	public void caching(SuiteTicketMessage suiteTicket) throws WeixinException {
		Token token = new Token(suiteTicket.getSuiteTicket());
		token.setExpiresIn(-1);
		tokenStorager.caching(getCacheKey(suiteTicket.getSuiteId()), token);
	}
}
