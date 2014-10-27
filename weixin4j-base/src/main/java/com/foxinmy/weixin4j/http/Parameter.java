package com.foxinmy.weixin4j.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Parameter {
	
	private final static String CHARSET = StandardCharsets.UTF_8.name();
	
	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Parameter() {

	}

	public Parameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String toGetPara() {
		try {
			return String.format("&%s=%s", name, URLEncoder.encode(value, CHARSET));
		} catch (UnsupportedEncodingException e) {
			return String.format("&%s=%s", name, value);
		}
	}

	public NameValuePair toPostPara() {
		try {
			return new BasicNameValuePair(name, URLEncoder.encode(value, CHARSET));
		} catch (UnsupportedEncodingException e) {
			return new BasicNameValuePair(name, value);
		}
	}

	@Override
	public String toString() {
		return String.format("[Parameter name=%s, value=%s]", name, value);
	}

}
