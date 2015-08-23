package com.foxinmy.weixin4j.qy.api;

import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.api.BaseApi;
import com.foxinmy.weixin4j.qy.model.WeixinQyAccount;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

/**
 * 微信企业号API
 * 
 * @className QyApi
 * @author jy.hu
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.api.BaseApi
 * @see <a href="http://qydev.weixin.qq.com/wiki/index.php">api文档</a>
 */
public class QyApi extends BaseApi {

	private final static ResourceBundle WEIXIN_BUNDLE;
	/**
	 * 默认使用weixin4j.properties文件中的企业号信息
	 */
	public final static WeixinQyAccount DEFAULT_WEIXIN_ACCOUNT;

	static {
		WEIXIN_BUNDLE = ResourceBundle
				.getBundle("com/foxinmy/weixin4j/qy/api/weixin");
		DEFAULT_WEIXIN_ACCOUNT = JSON.parseObject(
				Weixin4jConfigUtil.getValue("account"), WeixinQyAccount.class);
	}

	@Override
	protected ResourceBundle weixinBundle() {
		return WEIXIN_BUNDLE;
	}
}
