package com.foxinmy.weixin4j.qy.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 辅助API
 * 
 * @className HelperApi
 * @author jy
 * @date 2014年12月28日
 * @since JDK 1.7
 * @see
 */
public class HelperApi extends QyApi {
	private final TokenHolder tokenHolder;

	public HelperApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 获取微信服务器IP地址
	 * 
	 * @return IP地址
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%9B%9E%E8%B0%83%E6%A8%A1%E5%BC%8F#.E8.8E.B7.E5.8F.96.E5.BE.AE.E4.BF.A1.E6.9C.8D.E5.8A.A1.E5.99.A8.E7.9A.84ip.E6.AE.B5">获取IP地址</a>
	 * @throws WeixinException
	 */
	public List<String> getCallbackip() throws WeixinException {
		String getcallbackip_uri = getRequestUri("getcallbackip_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(getcallbackip_uri,
				token.getAccessToken()));
		return JSON.parseArray(response.getAsJson().getString("ip_list"),
				String.class);
	}
}
