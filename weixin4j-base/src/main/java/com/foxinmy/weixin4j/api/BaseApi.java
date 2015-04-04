package com.foxinmy.weixin4j.api;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.foxinmy.weixin4j.http.HttpRequest;
import com.foxinmy.weixin4j.xml.Map2ObjectConverter;
import com.foxinmy.weixin4j.xml.XmlStream;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.mapper.DefaultMapper;

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
	protected final HttpRequest request = new HttpRequest();
	protected final static XmlStream mapXstream = XmlStream.get();
	static {
		mapXstream.alias("xml", Map.class);
		mapXstream.registerConverter(new Map2ObjectConverter(new DefaultMapper(
				new ClassLoaderReference(XmlStream.class.getClassLoader()))));
	}

	protected String map2xml(Map<?, ?> map) {
		return mapXstream.toXML(map).replaceAll("__", "_");
	}

	@SuppressWarnings("unchecked")
	protected Map<String, String> xml2map(String xml) {
		return mapXstream.fromXML(xml, Map.class);
	}

	protected abstract ResourceBundle getWeixinBundle();

	protected String getRequestUri(String key) {
		String url = getConfigValue(key);
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
	
	protected String getConfigValue(String key) {
		return getWeixinBundle().getString(key);
	}
}
