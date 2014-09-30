package com.foxinmy.weixin4j.api;

import com.foxinmy.weixin4j.http.HttpRequest;

/**
 * 
 * @className BaseApi
 * @author jy.hu
 * @date 2014年9月26日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php">api文档</a>
 */
public class BaseApi {
	protected final HttpRequest request = new HttpRequest();
}
