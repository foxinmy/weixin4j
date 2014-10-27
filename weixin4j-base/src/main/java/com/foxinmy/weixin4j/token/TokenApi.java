package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;

/**
 * 获取Token接口
 * 
 * @className TokenApi
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.model.Token
 * @see com.foxinmy.weixin4j.token.AbstractTokenApi
 * @see com.foxinmy.weixin4j.token.FileTokenApi
 * @see com.foxinmy.weixin4j.token.RedisTokenApi
 */
public interface TokenApi {
	public Token getToken() throws WeixinException;
}
