package com.foxinmy.weixin4j.mp.token;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 微信公众平台TOKEN创建者
 * 
 * @className WeixinTokenCreator
 * @author jy
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/11/0e4b294685f817b95cbed85ba5e82b8f.html">微信公众平台获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinTokenCreator implements TokenCreator {

	private final WeixinRequestExecutor weixinExecutor;
	private final String appid;
	private final String secret;

	/**
	 * 
	 * @param appid
	 *            公众号ID
	 * @param secret
	 *            公众号secret
	 */
	public WeixinTokenCreator(String appid, String secret) {
		this.appid = appid;
		this.secret = secret;
		this.weixinExecutor = new WeixinRequestExecutor();
	}

	@Override
	public String getCacheKey() {
		return String.format("weixin4j_mp_token_%s", appid);
	}

	@Override
	public Token createToken() throws WeixinException {
		String tokenUrl = String.format(URLConsts.ASSESS_TOKEN_URL, appid,
				secret);
		WeixinResponse response = weixinExecutor.get(tokenUrl);
		Token token = response.getAsObject(new TypeReference<Token>() {
		});
		token.setCreateTime(System.currentTimeMillis());
		token.setOriginalResult(response.getAsString());
		return token;
	}
}
