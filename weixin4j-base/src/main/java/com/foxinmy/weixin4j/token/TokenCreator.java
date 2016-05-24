package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.model.Token;

/**
 * Token的创建
 *
 * @className TokenCreator
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see
 */
public abstract class TokenCreator implements CacheCreator<Token> {
	protected final WeixinRequestExecutor weixinExecutor;

	public TokenCreator() {
		this.weixinExecutor = new WeixinRequestExecutor();
	}

	/**
	 * 缓存key:附加weixin4j_前缀
	 *
	 * @return
	 */
	@Override
	public String key() {
		return String.format("weixin4j_%s", key0());
	}

	/**
	 * 返回缓存KEY的名称:建议接口类型命名 如 mp_token_{appid}
	 *
	 * @return
	 */
	public abstract String key0();
}
