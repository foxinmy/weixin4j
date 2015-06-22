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
 * @since JDK 1.7
 * @see
 */
public class SuitePerCodeHolder {

	public final TokenStorager tokenStorager;

	public SuitePerCodeHolder(TokenStorager tokenStorager) {
		this.tokenStorager = tokenStorager;
	}

	/**
	 * 缓存永久授权码
	 * 
	 * @param suiteId
	 * @param permanentCode
	 * @throws WeixinException
	 */
	public void caching(String suiteId, String permanentCode)
			throws WeixinException {
		Token token = new Token(permanentCode);
		token.setExpiresIn(-1);
		tokenStorager.caching(getCacheKey(suiteId), token);
	}

	/**
	 * 获取永久授权码的key
	 * 
	 * @param suiteId
	 * @return
	 */
	private String getCacheKey(String suiteId) {
		return String.format("qy_suite_percode_%s", suiteId);
	}

	/**
	 * 查找永久二维码
	 * 
	 * @param suiteId
	 * @return
	 * @throws WeixinException
	 */
	public String lookup(String suiteId) throws WeixinException {
		return tokenStorager.lookup(getCacheKey(suiteId)).getAccessToken();
	}
}
