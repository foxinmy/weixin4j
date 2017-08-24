package com.foxinmy.weixin4j.qy.api;

import java.util.ResourceBundle;

import com.foxinmy.weixin4j.api.BaseApi;

/**
 * 微信企业号API
 * 
 * @className QyApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月18日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.api.BaseApi
 * @see <a href="http://work.weixin.qq.com/api/doc#10012">api文档</a>
 */
public class QyApi extends BaseApi {

	private final static ResourceBundle WEIXIN_BUNDLE;
	static {
		WEIXIN_BUNDLE = ResourceBundle
				.getBundle("com/foxinmy/weixin4j/qy/api/weixin");
	}

	@Override
	protected ResourceBundle weixinBundle() {
		return WEIXIN_BUNDLE;
	}
}
