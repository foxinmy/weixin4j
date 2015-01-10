package com.foxinmy.weixin4j.token;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.type.AccountType;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 微信TOKEN创建者
 * 
 * @className WeixinTokenCreator
 * @author jy
 * @date 2015年1月10日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/11/0e4b294685f817b95cbed85ba5e82b8f.html">微信公众平台获取token说明</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%BB%E5%8A%A8%E8%B0%83%E7%94%A8">微信企业号获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinTokenCreator implements TokenCreator {

	private final HttpRequest request;
	private final WeixinAccount weixinAccount;

	public WeixinTokenCreator(AccountType accountType) {
		this(ConfigUtil.getWeixinAccount(accountType.getClazz()));
	}

	public WeixinTokenCreator(WeixinAccount weixinAccount) {
		this.request = new HttpRequest();
		this.weixinAccount = weixinAccount;
	}

	@Override
	public String getCacheKey() {
		return weixinAccount.getId();
	}

	@Override
	public Token createToken() throws WeixinException {
		Response response = request.get(weixinAccount.getTokenUrl());
		Token token = response.getAsObject(new TypeReference<Token>() {
		});
		token.setTime(System.currentTimeMillis());
		return token;
	}
}
