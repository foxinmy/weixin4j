package com.foxinmy.weixin4j.http.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.URLParameter;
import com.foxinmy.weixin4j.util.Consts;
import com.foxinmy.weixin4j.util.URLEncodingUtil;

public class FormUrlEntity extends StringEntity {

	private static final String PARAMETER_SEPARATOR = "&";

	public FormUrlEntity(List<URLParameter> parameters) {
		super(formatParameters(parameters),
				ContentType.APPLICATION_FORM_URLENCODED);
	}

	public FormUrlEntity(Map<String, String> parameters) {
		super(formatParameters(parameters),
				ContentType.APPLICATION_FORM_URLENCODED);
	}

	public static String formatParameters(List<URLParameter> parameters) {
		StringBuilder body = new StringBuilder();
		URLParameter parameter = parameters.get(0);
		body.append(parameter.encoding());
		for (int i = 1; i < parameters.size(); i++) {
			body.append(PARAMETER_SEPARATOR);
			parameter = parameters.get(i);
			body.append(parameter.encoding());
		}
		return body.toString();
	}

	public static String formatParameters(Map<String, String> parameters) {
		StringBuilder body = new StringBuilder();
		Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
		it.hasNext();
		Entry<String, String> parameter = it.next();
		body.append(String.format("%s=%s", URLEncodingUtil.encoding(
				parameter.getKey(), Consts.UTF_8, true), URLEncodingUtil
				.encoding(parameter.getValue(), Consts.UTF_8, true)));
		while (it.hasNext()) {
			parameter = it.next();
			body.append(PARAMETER_SEPARATOR).append(
					String.format("%s=%s", URLEncodingUtil.encoding(
							parameter.getKey(), Consts.UTF_8, true),
							URLEncodingUtil.encoding(parameter.getValue(),
									Consts.UTF_8, true)));
		}
		return body.toString();
	}
}
