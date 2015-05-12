package com.foxinmy.weixin4j.http;

import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.xml.XmlStream;

public class Response {

	private String text;
	private int statusCode;
	private String statusText;
	private byte[] body;
	private InputStream stream;
	private boolean isJsonResult;
	private boolean isXmlResult;

	public Response() {
	}

	public Response(String text) {
		this.text = text;
	}

	public void setJsonResult(boolean isJsonResult) {
		this.isJsonResult = isJsonResult;
	}

	public void setXmlResult(boolean isXmlResult) {
		this.isXmlResult = isXmlResult;
	}

	public String getAsString() {
		return text;
	}

	public JsonResult getAsJsonResult() {
		return JSON.parseObject(text, JsonResult.class);
	}

	public JSONObject getAsJson() {
		return JSON.parseObject(text);
	}

	public <T> T getAsObject(TypeReference<T> typeReference) {
		if (isJsonResult) {
			return JSON.parseObject(text, typeReference);
		}
		if (isXmlResult) {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) typeReference.getType();
			return XmlStream.get(text, clazz);
		}
		return null;
	}

	public XmlResult getAsXmlResult() {
		return XmlStream.get(text, XmlResult.class);
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public byte[] getBody() {
		return (byte[]) body.clone();
	}

	/**
	 * May expose internal representation by incorporating reference to mutable
	 * object
	 */
	public void setBody(byte[] body) {
		this.body = (byte[]) body.clone();
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public String toString() {
		return "Response [text=" + text + ", statusCode=" + statusCode
				+ ", statusText=" + statusText + ", stream=" + stream
				+ ", isJsonResult=" + isJsonResult + ", isXmlResult="
				+ isXmlResult + "]";
	}
}
