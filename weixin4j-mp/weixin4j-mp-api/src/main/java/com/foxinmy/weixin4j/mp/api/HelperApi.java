package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.model.SemQuery;
import com.foxinmy.weixin4j.mp.model.SemResult;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 辅助相关API
 * 
 * @className HelperApi
 * @author jy.hu
 * @date 2014年9月26日
 * @since JDK 1.7
 * @see
 */
public class HelperApi extends MpApi {

	private final TokenHolder tokenHolder;

	public HelperApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 长链接转短链接
	 * 
	 * @param url
	 * @return 短链接
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/10/165c9b15eddcfbd8699ac12b0bd89ae6.html">长链接转短链接</a>
	 */
	public String getShorturl(String url) throws WeixinException {
		String shorturl_uri = getRequestUri("shorturl_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("action", "long2short");
		obj.put("long_url", url);
		Response response = request.post(
				String.format(shorturl_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJson().getString("short_url");
	}

	/**
	 * 语义理解
	 * 
	 * @param semQuery
	 *            语义理解协议
	 * @return 语义理解结果
	 * @see com.foxinmy.weixin4j.mp.model.SemQuery
	 * @see com.foxinmy.weixin4j.mp.model.SemResult
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/0ce78b3c9524811fee34aba3e33f3448.html">语义理解</a>
	 * @throws WeixinException
	 */
	public SemResult semantic(SemQuery semQuery) throws WeixinException {
		WeixinMpAccount weixinAccount = (WeixinMpAccount) tokenHolder.getAccount();
		String semantic_uri = getRequestUri("semantic_uri");
		Token token = tokenHolder.getToken();
		semQuery.appid(weixinAccount.getId());
		Response response = request.post(
				String.format(semantic_uri, token.getAccessToken()),
				semQuery.toJson());
		return response.getAsObject(new TypeReference<SemResult>() {
		});
	}

	/**
	 * 获取微信服务器IP地址
	 * 
	 * @return IP地址
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/2ad4b6bfd29f30f71d39616c2a0fcedc.html">获取IP地址</a>
	 * @throws WeixinException
	 */
	public List<String> getcallbackip() throws WeixinException {
		String getcallbackip_uri = getRequestUri("getcallbackip_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(String.format(getcallbackip_uri,
				token.getAccessToken()));
		return JSON.parseArray(response.getAsJson().getString("ip_list"),
				String.class);
	}
}