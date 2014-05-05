package com.foxinmy.weixin4j.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.NameValuePair;

public class Parameter {
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
			return String.format("&%s=%s", URLEncoder.encode(name, "UTF-8"), URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return String.format("&%s=%s", name, value);
		}
	}

	public NameValuePair toPostPara() {
		try {
			return new NameValuePair(URLEncoder.encode(name, "UTF-8"), URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return new NameValuePair(name, value);
		}
	}

	@Override
	public String toString() {
		return String.format("[Parameter name=%s, value=%s]", name,value);
	}
	
}
