package com.foxinmy.weixin4j.qy.token;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.token.TokenCreator;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 微信企业号TOKEN创建者
 * 
 * @className WeixinTokenCreator
 * @author jy
 * @date 2015年1月10日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%BB%E5%8A%A8%E8%B0%83%E7%94%A8">微信企业号获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinTokenCreator implements TokenCreator {

	private final HttpRequest request;
	private final String corpid;
	private final String corpsecret;

	public WeixinTokenCreator() {
		WeixinAccount weixinAccount = ConfigUtil.getWeixinAccount();
		this.corpid = weixinAccount.getId();
		this.corpsecret = weixinAccount.getSecret();
		this.request = new HttpRequest();
	}

	public WeixinTokenCreator(String corpid, String corpsecret) {
		this.corpid = corpid;
		this.corpsecret = corpsecret;
		this.request = new HttpRequest();
	}

	@Override
	public String getCacheKey() {
		return String.format("qy_token_%s", corpid);
	}

	@Override
	public Token createToken() throws WeixinException {
		String tokenUrl = String.format(Consts.QY_ASSESS_TOKEN_URL, corpid,
				corpsecret);
		Response response = request.get(tokenUrl);
		Token token = response.getAsObject(new TypeReference<Token>() {
		});
		token.setTime(System.currentTimeMillis());
		return token;
	}
}
