package com.foxinmy.weixin4j.http.weixin;

import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

public class WeixinResponse {

	private boolean isJsonResult;
	private boolean isXmlResult;
	private volatile String text;

	private final HttpResponse response;

	public WeixinResponse(HttpResponse response) {
		this.response = response;
	}

	public void setJsonResult(boolean isJsonResult) {
		this.isJsonResult = isJsonResult;
	}

	public void setXmlResult(boolean isXmlResult) {
		this.isXmlResult = isXmlResult;
	}

	public String getAsString() {
		if (text == null) {
			text = StringUtil.newStringUtf8(response.getContent());
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public JsonResult getAsJsonResult() {
		return JSON.parseObject(getAsString(), JsonResult.class);
	}

	public JSONObject getAsJson() {
		return JSON.parseObject(getAsString());
	}

	public <T> T getAsObject(TypeReference<T> typeReference) {
		if (isJsonResult) {
			return JSON.parseObject(getAsString(), typeReference);
		}
		if (isXmlResult) {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) typeReference.getType();
			return XmlStream.fromXML(getAsString(), clazz);
		}
		return null;
	}

	public XmlResult getAsXmlResult() {
		return XmlStream.fromXML(getAsString(), XmlResult.class);
	}

	public HttpHeaders getHeaders() {
		return response.getHeaders();
	}

	public HttpStatus getStatus() {
		return response.getStatus();
	}

	public InputStream getBody() {
		return response.getBody();
	}
}
