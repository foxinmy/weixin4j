package com.foxinmy.weixin4j.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * 签名工具类
 * @className MapUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.7
 * @see
 */
public class MapUtil {
	private final static Charset charset = StandardCharsets.UTF_8;

	public static String toJoinString(Object object, boolean encoder,
			boolean lowerCase, JSONObject extra) {
		String text = JSON.toJSONString(object);
		Map<String, String> map = new TreeMap<String, String>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
				});
		map.putAll(JSON.parseObject(text,
				new TypeReference<Map<String, String>>() {
				}));
		if (extra != null && !extra.isEmpty()) {
			for (String key : extra.keySet()) {
				map.put(key, extra.getString(key));
			}
		}
		StringBuilder sb = new StringBuilder();
		Set<Map.Entry<String, String>> set = map.entrySet();
		try {
			if (encoder && lowerCase) {
				for (Map.Entry<String, String> entry : set) {
					sb.append(entry.getKey().toLowerCase())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(),
									charset.name())).append("&");
				}
			} else if (encoder) {
				for (Map.Entry<String, String> entry : set) {
					sb.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(),
									charset.name())).append("&");
				}
			} else if (lowerCase) {
				for (Map.Entry<String, String> entry : set) {
					sb.append(entry.getKey().toLowerCase()).append("=")
							.append(entry.getValue()).append("&");
				}
			} else {
				for (Map.Entry<String, String> entry : set) {
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
