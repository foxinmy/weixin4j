package com.foxinmy.weixin4j.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.foxinmy.weixin4j.model.Consts;

/**
 * 键值对参数
 * 
 * @className UrlEncodeParameter
 * @author jy
 * @date 2015年5月29日
 * @since JDK 1.7
 * @see
 */
public class UrlEncodeParameter extends NameValue {

	private static final long serialVersionUID = -115491642760990655L;

	public UrlEncodeParameter(String name, String value) {
		super(name, value);
	}

	public String encodingParameter() {
		try {
			return String.format("&%s=%s", getName(),
					URLEncoder.encode(getValue(), Consts.UTF_8.name()));
		} catch (UnsupportedEncodingException e) {
			return String.format("&%s=%s", getName(), getValue());
		}
	}

	@Override
	public String toString() {
		return String.format("[HttpParameter name=%s, value=%s]", getName(),
				getValue());
	}
}
