package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.model.Token;

/**
 * 第三方应用永久授权码的存取
 *
 * @className PerTicketManager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月22日
 * @since JDK 1.6
 */
public class PerTicketManager extends TicketManager {

	private final String authAppId;

	public PerTicketManager(String authAppId, String thirdId,
			String thirdSecret, CacheStorager<Token> cacheStorager) {
		super(thirdId, thirdSecret, cacheStorager);
		this.authAppId = authAppId;
	}

	/**
	 * 获取永久授权码的key
	 *
	 * @return
	 */
	@Override
	public String getCacheKey() {
		return String.format("%sthird_party_percode_ticket_%s_%s",
				TokenCreator.CACHEKEY_PREFIX, getThirdId(), authAppId);
	}

	public String getAuthAppId() {
		return authAppId;
	}
}
