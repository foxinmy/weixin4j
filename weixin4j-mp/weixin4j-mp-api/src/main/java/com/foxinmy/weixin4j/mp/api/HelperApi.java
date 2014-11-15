package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
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
public class HelperApi extends BaseApi {

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
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%95%BF%E9%93%BE%E6%8E%A5%E8%BD%AC%E7%9F%AD%E9%93%BE%E6%8E%A5%E6%8E%A5%E5%8F%A3">长链接转短链接</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%AF%AD%E4%B9%89%E7%90%86%E8%A7%A3">语义理解</a>
	 * @throws WeixinException
	 */
	public SemResult semantic(SemQuery semQuery) throws WeixinException {
		WeixinAccount weixinAccount = tokenHolder.getAccount();
		String semantic_uri = getRequestUri("semantic_uri");
		Token token = tokenHolder.getToken();
		semQuery.appid(weixinAccount.getAppId());
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
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%BE%AE%E4%BF%A1%E6%9C%8D%E5%8A%A1%E5%99%A8IP%E5%9C%B0%E5%9D%80">获取IP地址</a>
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