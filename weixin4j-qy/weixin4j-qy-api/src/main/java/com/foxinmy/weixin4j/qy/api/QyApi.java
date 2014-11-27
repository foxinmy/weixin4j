package com.foxinmy.weixin4j.qy.api;

import java.util.ResourceBundle;

import com.foxinmy.weixin4j.api.BaseApi;

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
	static {
		weixinBundle = ResourceBundle
				.getBundle("com/foxinmy/weixin4j/qy/api/weixin");
	}
}
