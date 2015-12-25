package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * TOKEN创建者
 * 
 * @className TokenCreator
 * @author jy
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see
 */
public interface TokenCreator {
	/**
	 * 返回缓存KEY的名称
	 * 
	 * @return
	 */
	public String getCacheKey();

	/**
	 * 创建token
	 * 
	 * @return
	 * @throws WeixinException
	 */
	public Token createToken() throws WeixinException;
}
