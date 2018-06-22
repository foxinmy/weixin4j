package com.foxinmy.weixin4j.wxa.api;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.api.BaseApi;

/**
 * @since 1.8
 */
abstract class WxaApi extends BaseApi {

	private static final ResourceBundle WEIXIN_BUNDLE;

	static {
		WEIXIN_BUNDLE = ResourceBundle
			.getBundle("com/foxinmy/weixin4j/wxa/api/weixin");
	}

	private final Properties cache = new Properties();
	private final Properties properties;
	private final Pattern uriPattern = Pattern.compile("(\\{[^\\}]*\\})");

	public WxaApi() {
		this(null);
	}

	/**
	 * Constructs {@link WxaApi} with specified {@code properties}.
	 *
	 * @param properties the properties to override the {@link #weixinBundle()}.
	 */
	public WxaApi(Properties properties) {
		this.properties = properties;
	}

	@Override
	protected ResourceBundle weixinBundle() {
		return WEIXIN_BUNDLE;
	}

	@Override
	protected String getRequestUri(String key) {
		String url = this.cache.getProperty(key);
		if (url != null) {
			return url;
		}

		if (this.properties != null && (url = this.properties.getProperty(key)) != null) {
			Matcher m = uriPattern.matcher(url);
			StringBuffer sb = new StringBuffer();
			String sub = null;
			while (m.find()) {
				sub = m.group();
				m.appendReplacement(sb,
					getRequestUri(sub.substring(1, sub.length() - 1)));
			}
			m.appendTail(sb);
			url = sb.toString();
		} else {
			url = super.getRequestUri(key);
		}

		this.cache.setProperty(key, url);

		return url;
	}

	String getRequestUri(String key, Object... args) {
		return String.format(getRequestUri(key), args);
	}

}
