package com.foxinmy.weixin4j.mp.api;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.xml.XStream;

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
	protected final XStream xStream = XStream.get();
	protected final Charset utf8 = StandardCharsets.UTF_8;
	private final static ResourceBundle weixinBundle;
	static {
		weixinBundle = ResourceBundle
				.getBundle("com/foxinmy/weixin4j/mp/api/weixin");
	}

	protected String getRequestUri(String key) {
		String url = weixinBundle.getString(key);
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
