package com.foxinmy.weixin4j.qy.suite;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenStorager;

/**
 * 应用套件永久授权码的存取
 * 
 * @className SuitePerCodeHolder
 * @author jy
 * @date 2015年6月22日
 * @since JDK 1.6
 * @see
 */
public class SuitePerCodeHolder {

	private final String authCorpId;
	private final String suiteId;
	private final TokenStorager tokenStorager;

	public SuitePerCodeHolder(String authCorpId, String suiteId,
			TokenStorager tokenStorager) {
		this.authCorpId = authCorpId;
		this.suiteId = suiteId;
		this.tokenStorager = tokenStorager;
	}

	/**
	 * 缓存永久授权码
	 * 
	 * @param permanentCode
	 * @throws WeixinException
	 */
	public void cachingPermanentCode(String permanentCode)
			throws WeixinException {
		Token token = new Token(permanentCode);
		token.setExpiresIn(-1);
		tokenStorager.caching(getCacheKey(), token);
	}

	/**
	 * 获取永久授权码的key
	 * 
	 * @return
	 */
	public String getCacheKey() {
		return String.format("weixin4j_qy_suite_percode_%s:%s", suiteId,
				authCorpId);
	}

	/**
	 * 查找永久授权码
	 * 
	 * @return
	 * @throws WeixinException
	 */
	public String getPermanentCode() throws WeixinException {
		return tokenStorager.lookup(getCacheKey()).getAccessToken();
	}

	public String getSuiteId() {
		return this.suiteId;
	}

	public String getAuthCorpId() {
		return this.authCorpId;
	}

	public TokenStorager getTokenStorager() {
		return this.tokenStorager;
	}
}
