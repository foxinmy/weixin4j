package com.foxinmy.weixin4j.api;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.http.weixin.WeixinHttpClient;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.token.TokenStorager;
import com.foxinmy.weixin4j.util.ConfigUtil;
import com.foxinmy.weixin4j.util.Weixin4jConst;

/**
 * API基础
 * 
 * @className BaseApi
 * @author jy.hu
 * @date 2014年9月26日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php">微信公众平台API文档</a>
 * @see <a href="http://qydev.weixin.qq.com/wiki/index.php">微信企业号API文档</a>
 */
public abstract class BaseApi {

	protected final WeixinHttpClient weixinClient;

	protected abstract ResourceBundle weixinBundle();

	public BaseApi() {
		this.weixinClient = new WeixinHttpClient();
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

	/**
	 * 默认使用weixin4j.properties文件中的公众号信息
	 */
	public final static WeixinAccount DEFAULT_WEIXIN_ACCOUNT;

	/**
	 * 默认token使用File的方式存储
	 */
	public final static TokenStorager DEFAULT_TOKEN_STORAGER;

	static {
		DEFAULT_WEIXIN_ACCOUNT = ConfigUtil.getWeixinAccount();
		DEFAULT_TOKEN_STORAGER = new FileTokenStorager(ConfigUtil.getValue(
				"token_path", Weixin4jConst.DEFAULT_TOKEN_PATH));
	}
}
