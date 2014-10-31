package com.foxinmy.weixin4j.mp.api;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
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

	private final TokenHolder tokenApi;

	public HelperApi(TokenHolder tokenApi) {
		this.tokenApi = tokenApi;
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
		Token token = tokenApi.getToken();
		JSONObject obj = new JSONObject();
		obj.put("action", "long2short");
		obj.put("long_url", url);
		Response response = request.post(
				String.format(shorturl_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJson().getString("short_url");
	}
}
