package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 获取weixin.properties中的appid&appsecret信息
 * 
 * @className AbstractTokenHolder
 * @author jy
 * @date 2014年10月6日
 * @since JDK 1.7
 * @see
 */
public abstract class AbstractTokenHolder implements TokenHolder {
	protected final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	protected final HttpRequest request = new HttpRequest();
	private final WeixinAccount weixinAccount;

	public AbstractTokenHolder() {
		this.weixinAccount = ConfigUtil.getWeixinAccount();
	}

	public AbstractTokenHolder(WeixinAccount weixinAccount) {
		this.weixinAccount = weixinAccount;
	}

	public WeixinAccount getAccount() {
		return weixinAccount;
	}
}
