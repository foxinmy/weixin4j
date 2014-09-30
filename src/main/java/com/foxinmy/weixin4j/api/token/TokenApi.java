package com.foxinmy.weixin4j.api.token;

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
 * @see com.foxinmy.weixin4j.api.token.FileTokenApi
 * @see com.foxinmy.weixin4j.api.token.RedisTokenApi
 */
public interface TokenApi {
	public Token getToken() throws WeixinException;
}
