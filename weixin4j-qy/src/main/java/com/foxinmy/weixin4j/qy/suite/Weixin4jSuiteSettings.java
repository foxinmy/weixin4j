package com.foxinmy.weixin4j.qy.suite;

import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.cache.CacheStorager;
import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.qy.model.WeixinQyAccount;
import com.foxinmy.weixin4j.setting.SystemSettings;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信第三方套件配置相关
 *
 * @className Weixin4jSuiteSettings
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年1月28日
 * @since JDK 1.6
 * @see
 */
public class Weixin4jSuiteSettings extends SystemSettings<WeixinQyAccount> {
	/**
	 * Token的存储方式 默认为FileCacheStorager
	 */
	private CacheStorager<Token> cacheStorager;

	/**
	 * 默认使用weixin4j.properties配置的信息
	 */
	public Weixin4jSuiteSettings() {
		this(JSON.parseObject(Weixin4jConfigUtil.getValue("account"),
				WeixinQyAccount.class));
	}

	/**
	 *
	 * @param providerCorpId
	 *            服务商的企业号ID <font color="red">使用服务商API时必填项</font>
	 * @param providerSecret
	 *            服务商secret <font color="red">使用服务商API时必填项</font>
	 * @param suites
	 *            套件信息 <font color="red">使用套件API时必填项</font>
	 */
	public Weixin4jSuiteSettings(String providerCorpId, String providerSecret,
			WeixinAccount... suites) {
		this(new WeixinQyAccount(providerCorpId, null, Arrays.asList(suites),
				providerSecret, null));
	}

	/**
	 * 账号信息
	 *
	 * @param account
	 */
	private Weixin4jSuiteSettings(WeixinQyAccount account) {
		super(account);
	}

	@Override
	public String getTmpdir0() {
		if (StringUtil.isBlank(getTmpdir())) {
			return Weixin4jConfigUtil.getClassPathValue("weixin4j.tmpdir",
					System.getProperty("java.io.tmpdir"));
		}
		return getTmpdir();
	}

	public CacheStorager<Token> getCacheStorager() {
		return cacheStorager;
	}

	public CacheStorager<Token> getCacheStorager0() {
		if (cacheStorager == null) {
			return new FileCacheStorager<Token>(getTmpdir0());
		}
		return cacheStorager;
	}

	@Override
	public String toString() {
		return "Weixin4jSuiteSettings [" + super.toString() + "]";
	}
}
