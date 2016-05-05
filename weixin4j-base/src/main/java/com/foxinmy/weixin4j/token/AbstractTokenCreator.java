package com.foxinmy.weixin4j.token;

import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;

/**
 * 
 * @className: AbstractTokenCreator
 * @author jinyu
 * @date 2016年4月21日
 * @since JDK 1.6
 * @see
 */
public abstract class AbstractTokenCreator implements TokenCreator {

	protected final WeixinRequestExecutor weixinExecutor;

	public AbstractTokenCreator() {
		this.weixinExecutor = new WeixinRequestExecutor();
	}

	/**
	 * 缓存key:附加weixin4j_前缀
	 * 
	 * @return
	 */
	public String getCacheKey() {
		return String.format("weixin4j_%s", getCacheKey0());
	}

	/**
	 * 返回缓存KEY的名称:建议接口类型命名 如 mp_token_{appid}
	 * 
	 * @return
	 */
	public abstract String getCacheKey0();
}
