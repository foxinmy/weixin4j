package com.foxinmy.weixin4j.qy.token;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.type.URLConsts;
import com.foxinmy.weixin4j.token.AbstractTokenCreator;

/**
 * 微信企业号TOKEN创建
 * 
 * @className WeixinTokenCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E4%B8%BB%E5%8A%A8%E8%B0%83%E7%94%A8">
 *      微信企业号获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinTokenCreator extends AbstractTokenCreator {

	private final String corpid;
	private final String corpsecret;

	/**
	 * 
	 * @param corpid
	 *            企业号ID
	 * @param corpsecret
	 *            企业号secret
	 */
	public WeixinTokenCreator(String corpid, String corpsecret) {
		this.corpid = corpid;
		this.corpsecret = corpsecret;
	}

	@Override
	public String getCacheKey0() {
		return String.format("qy_token_%s", corpid);
	}

	@Override
	public Token createToken() throws WeixinException {
		String tokenUrl = String.format(URLConsts.ASSESS_TOKEN_URL, corpid,
				corpsecret);
		WeixinResponse response = weixinExecutor.get(tokenUrl);
		Token token = response.getAsObject(new TypeReference<Token>() {
		});
		token.setCreateTime(System.currentTimeMillis());
		token.setOriginalResult(response.getAsString());
		return token;
	}
}
