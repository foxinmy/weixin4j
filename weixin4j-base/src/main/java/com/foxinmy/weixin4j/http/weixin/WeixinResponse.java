package com.foxinmy.weixin4j.http.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.util.StringUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

public class WeixinResponse extends HttpResponse {

	private boolean isJsonResult;
	private boolean isXmlResult;
	private String text;

	public void setJsonResult(boolean isJsonResult) {
		this.isJsonResult = isJsonResult;
	}

	public void setXmlResult(boolean isXmlResult) {
		this.isXmlResult = isXmlResult;
	}

	public String getAsString() {
		if (text == null) {
			text = StringUtil.newStringUtf8(getContent());
		}
		return text;
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
			return XmlStream.get(getAsString(), clazz);
		}
		return null;
	}

	public XmlResult getAsXmlResult() {
		return XmlStream.get(getAsString(), XmlResult.class);
	}
}
