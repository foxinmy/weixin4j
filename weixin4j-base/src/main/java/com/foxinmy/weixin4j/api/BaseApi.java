package com.foxinmy.weixin4j.api;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.http.weixin.WeixinRequestExecutor;
import com.foxinmy.weixin4j.logging.InternalLogger;
import com.foxinmy.weixin4j.logging.InternalLoggerFactory;

/**
 * API基础
 *
 * @className BaseApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月26日
 * @since JDK 1.6
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php">微信公众平台API文档</a>
 * @see <a href="http://qydev.weixin.qq.com/wiki/index.php">微信企业号API文档</a>
 */
public abstract class BaseApi {

	protected final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	protected final WeixinRequestExecutor weixinExecutor;

	protected abstract ResourceBundle weixinBundle();

	public BaseApi() {
		this.weixinExecutor = new WeixinRequestExecutor();
	}

	protected String getRequestUri(String key) {
		String url = weixinBundle().getString(key);
		Pattern p = Pattern.compile("(\\{[^\\}]*\\})");
		Matcher m = p.matcher(url);
		StringBuffer sb = new StringBuffer();
		String sub = null;
		while (m.find()) {
			sub = m.group();
			m.appendReplacement(sb,
					getRequestUri(sub.substring(1, sub.length() - 1)));
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
