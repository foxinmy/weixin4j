package com.foxinmy.weixin4j.qy.token;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 微信企业号应用提供商凭证创建
 * 
 * @className WeixinTokenCreator
 * @author jy
 * @date 2015年1月10日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%BA%94%E7%94%A8%E6%8F%90%E4%BE%9B%E5%95%86%E5%87%AD%E8%AF%81">获取应用提供商凭证</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinProviderTokenCreator implements TokenCreator {

	private final WeixinRequestExecutor weixinExecutor;
	private final String corpid;
	private final String providersecret;

	/**
	 * 
	 * @param corpid
	 *            企业号ID
	 * @param providersecret
	 *            企业号提供商的secret
	 */
	public WeixinProviderTokenCreator(String corpid, String providersecret) {
		this.corpid = corpid;
		this.providersecret = providersecret;
		this.weixinExecutor = new WeixinRequestExecutor();
	}

	@Override
	public String getCacheKey() {
		return String.format("qy_provider_token_%s", corpid);
	}

	@Override
	public Token createToken() throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("corpid", corpid);
		obj.put("provider_secret", providersecret);
		WeixinResponse response = weixinExecutor.post(URLConsts.PROVIDER_TOKEN_URL,
				obj.toJSONString());
		obj = response.getAsJson();
		Token token = new Token(obj.getString("provider_access_token"));
		token.setExpiresIn(obj.getIntValue("expires_in"));
		token.setTime(System.currentTimeMillis());
		return token;
	}
}
