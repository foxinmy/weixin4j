package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * token持有者
 * 
 * @className TokenHolder
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.model.Token
 * @see com.foxinmy.weixin4j.token.FileTokenHolder
 * @see com.foxinmy.weixin4j.token.RedisTokenHolder
 */
public interface TokenHolder {
	public Token getToken() throws WeixinException;
}
