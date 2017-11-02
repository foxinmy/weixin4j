package com.foxinmy.weixin4j.qy.token;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.TokenCreator;

/**
 * 微信企业号应用提供商凭证创建
 *
 * @className WeixinProviderTokenCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see <a href= "http://work.weixin.qq.com/api/doc#11791/服务商的凭证">服务商的凭证</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinProviderTokenCreator extends TokenCreator {

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
	}

	@Override
	public String name() {
		return "qy_provider_token";
	}

	@Override
	public String uniqueid() {
		return corpid;
	}

	@Override
	public Token create() throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("corpid", corpid);
		obj.put("provider_secret", providersecret);
		WeixinResponse response = weixinExecutor.post(
				URLConsts.PROVIDER_TOKEN_URL, obj.toJSONString());
		obj = response.getAsJson();
		return new Token(obj.getString("provider_access_token"),
				obj.getLongValue("expires_in") * 1000l);
	}
}
