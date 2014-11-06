package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;

/**
 * 获取Token接口
 * 
 * @className TokenHolder
 * @author jy.hu
 * @date 2014年9月27日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.model.Token
 * @see com.foxinmy.weixin4j.token.AbstractTokenHolder
 * @see com.foxinmy.weixin4j.token.FileTokenHolder
 * @see com.foxinmy.weixin4j.token.RedisTokenHolder
 */
public interface TokenHolder {
	public WeixinAccount getAccount();

	public Token getToken() throws WeixinException;
}
