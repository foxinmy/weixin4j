package com.foxinmy.weixin4j.mp.token;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 微信公众平台TOKEN创建者
 * 
 * @className WeixinTokenCreator
 * @author jy
 * @date 2015年1月10日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/11/0e4b294685f817b95cbed85ba5e82b8f.html">微信公众平台获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinTokenCreator implements TokenCreator {

	private final HttpRequest request;
	private final String appid;
	private final String secret;

	public WeixinTokenCreator() {
		WeixinMpAccount weixinAccount = ConfigUtil.getWeixinMpAccount();
		this.appid = weixinAccount.getId();
		this.secret = weixinAccount.getSecret();
		this.request = new HttpRequest();
	}

	public WeixinTokenCreator(String appid, String secret) {
		this.appid = appid;
		this.secret = secret;
		this.request = new HttpRequest();
	}

	@Override
	public String getCacheKey() {
		return String.format("mp_token_%s", appid);
	}

	@Override
	public Token createToken() throws WeixinException {
		String tokenUrl = String.format(Consts.MP_ASSESS_TOKEN_URL, appid,
				secret);
		Response response = request.get(tokenUrl);
		Token token = response.getAsObject(new TypeReference<Token>() {
		});
		token.setTime(System.currentTimeMillis());
		return token;
	}
}
