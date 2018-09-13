package com.foxinmy.weixin4j.wxa.api;

import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.token.TokenManager;

abstract class TokenManagerApi extends WxaApi {

	private final TokenManager tokenManager;

	public TokenManagerApi(final TokenManager tokenManager) {
		this(tokenManager, null);
	}

	public TokenManagerApi(final TokenManager tokenManager, final Properties properties) {
		super(properties);
		this.tokenManager = tokenManager;
	}

	<T> T post(String key, Object params, TypeReference<T> typeReference) throws WeixinException {
		final String uri = this.getAccessTokenRequestUri(key);
		final String body = JSON.toJSONString(params);
		final WeixinResponse response = this.weixinExecutor.post(uri, body);
		return response.getAsObject(typeReference);
	}

	String getAccessTokenRequestUri(String key) throws WeixinException {
		final String accessToken = tokenManager.getAccessToken();
		final String uri = this.getRequestUri(key, accessToken);
		return uri;
	}

}
