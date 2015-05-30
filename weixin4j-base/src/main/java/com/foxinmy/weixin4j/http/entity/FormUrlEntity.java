package com.foxinmy.weixin4j.http.entity;

import java.util.List;

import com.foxinmy.weixin4j.http.ContentType;
import com.foxinmy.weixin4j.http.UrlEncodeParameter;

public class FormUrlEntity extends StringEntity {
	private static final String PARAMETER_SEPARATOR = "&";

	public FormUrlEntity(List<UrlEncodeParameter> parameters) {
		super(formatParameters(parameters),
				ContentType.APPLICATION_FORM_URLENCODED);
	}

	private static String formatParameters(List<UrlEncodeParameter> parameters) {
		final StringBuilder body = new StringBuilder();
		UrlEncodeParameter parameter = parameters.get(0);
		body.append(parameter.encodingParameter());
		for (int i = 1; i < parameters.size(); i++) {
			body.append(PARAMETER_SEPARATOR);
			parameter = parameters.get(i);
			body.append(parameter.encodingParameter());
		}
		return body.toString();
	}
}
