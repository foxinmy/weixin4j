package com.foxinmy.weixin4j.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.xml.ListsuffixResultSerializer;

/**
 * 签名工具类
 * 
 * @className MapUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.6
 * @see
 */
public class MapUtil {
	/**
	 * 连接字符串
	 * 
	 * @param object
	 *            对象
	 * @param encoder
	 *            是否编码
	 * @param lowerCase
	 *            是否转换小写
	 * @param extra
	 *            附加对象
	 * @return
	 */
	public static String toJoinString(Object object, boolean encoder,
			boolean lowerCase, Map<String, String> extra) {
		Map<String, String> map = new TreeMap<String, String>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
				});
		JSONObject obj = null;
		if (object instanceof String) {
			obj = JSONObject.parseObject(object.toString());
		} else {
			obj = ListsuffixResultSerializer.serializeToJSON(object);
		}
		for (String key : obj.keySet()) {
			map.put(key, obj.getString(key));
		}
		if (extra != null && !extra.isEmpty()) {
			map.putAll(extra);
		}
		return toJoinString(map, encoder, lowerCase);
	}

	/**
	 * 连接字符串
	 * 
	 * @param map
	 *            对象
	 * @param encoder
	 *            是否编码
	 * @param lowerCase
	 *            是否转换小写
	 * @return
	 */
	public static String toJoinString(Map<String, String> map, boolean encoder,
			boolean lowerCase) {
		map.remove("sign");
		StringBuilder sb = new StringBuilder();
		Set<Map.Entry<String, String>> set = map.entrySet();
		try {
			if (encoder && lowerCase) {
				for (Map.Entry<String, String> entry : set) {
					if (StringUtil.isBlank(entry.getValue())) {
						continue;
					}
					sb.append(entry.getKey().toLowerCase())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(),
									Consts.UTF_8.name())).append("&");
				}
			} else if (encoder) {
				for (Map.Entry<String, String> entry : set) {
					if (StringUtil.isBlank(entry.getValue())) {
						continue;
					}
					sb.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(),
									Consts.UTF_8.name())).append("&");
				}
			} else if (lowerCase) {
				for (Map.Entry<String, String> entry : set) {
					if (StringUtil.isBlank(entry.getValue())) {
						continue;
					}
					sb.append(entry.getKey().toLowerCase()).append("=")
							.append(entry.getValue()).append("&");
				}
			} else {
				for (Map.Entry<String, String> entry : set) {
					if (StringUtil.isBlank(entry.getValue())) {
						continue;
					}
					sb.append(entry.getKey()).append("=")
							.append(entry.getValue()).append("&");
				}
			}
		} catch (UnsupportedEncodingException e) {
			;
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
