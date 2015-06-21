package com.foxinmy.weixin4j.mp.api;

import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.api.BaseApi;
import com.foxinmy.weixin4j.mp.model.WeixinMpAccount;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 微信公众平台API
 * 
 * @className MpApi
 * @author jy.hu
 * @date 2014年9月26日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.api.BaseApi
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php">api文档</a>
 */
public class MpApi extends BaseApi {
	
	private final static ResourceBundle WEIXIN_BUNDLE;
	/**
	 * 默认使用weixin4j.properties文件中的公众号信息
	 */
	public final static WeixinMpAccount DEFAULT_WEIXIN_ACCOUNT;
	
	static {
		WEIXIN_BUNDLE = ResourceBundle
				.getBundle("com/foxinmy/weixin4j/mp/api/weixin");
		DEFAULT_WEIXIN_ACCOUNT = JSON.parseObject(
				ConfigUtil.getValue("account"), WeixinMpAccount.class);
	}

	@Override
	protected String getConfigValue(String key) {
		return WEIXIN_BUNDLE.getString(key);
	}
}
