package com.foxinmy.weixin4j.http.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.http.HttpHeaders;
import com.foxinmy.weixin4j.http.HttpResponse;
import com.foxinmy.weixin4j.http.HttpStatus;
import com.foxinmy.weixin4j.http.HttpVersion;
import com.foxinmy.weixin4j.http.message.JsonMessageConverter;
import com.foxinmy.weixin4j.http.message.MessageConverter;
import com.foxinmy.weixin4j.http.message.XmlMessageConverter;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 调用微信接口响应
 * 
 * @className WeixinResponse
 * @author jinyu
 * @date Jul 21, 2016
 * @since JDK 1.6
 */
public class WeixinResponse implements HttpResponse {

	private volatile String text;
	private final HttpResponse response;
	private static List<MessageConverter> messageConverters = new ArrayList<MessageConverter>();
	private final TypeReference<ApiResult> APIRESULT_CLAZZ = new TypeReference<ApiResult>() {
	};
	private final TypeReference<XmlResult> XMLRESULT_CLAZZ = new TypeReference<XmlResult>() {
	};
	private final TypeReference<JSONObject> JSONOBJECT_CLAZZ = new TypeReference<JSONObject>() {
	};

	static {
		messageConverters.add(new JsonMessageConverter());
		messageConverters.add(new XmlMessageConverter());
	}

	public WeixinResponse(HttpResponse response) {
		this.response = response;
	}

	public String getAsString() {
		if (text == null) {
			text = StringUtil.newStringUtf8(getContent());
		}
		return text;
	}

	public ApiResult getAsResult() {
		return getAsObject(APIRESULT_CLAZZ);
	}

	public JSONObject getAsJson() {
		return getAsObject(JSONOBJECT_CLAZZ);
	}

	public XmlResult getAsXml() {
		return getAsObject(XMLRESULT_CLAZZ);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAsObject(TypeReference<T> typeReference) {
		Class<T> clazz = (Class<T>) typeReference.getType();
		for (MessageConverter messageConverter : messageConverters) {
			if (messageConverter.canConvert(clazz, response)) {
				try {
					return messageConverter.convert(clazz, response);
				} catch (IOException e) {
					throw new RuntimeException("IO error on convert to "
							+ clazz, e);
				}
			}
		}
		if (clazz.isAssignableFrom(ApiResult.class)) {
			return (T) new ApiResult();
		}
		throw new RuntimeException("cannot convert to " + clazz);
	}

	@Override
	public HttpHeaders getHeaders() {
		return response.getHeaders();
	}

	@Override
	public HttpStatus getStatus() {
		return response.getStatus();
	}

	@Override
	public byte[] getContent() {
		return response.getContent();
	}

	@Override
	public InputStream getBody() {
		return response.getBody();
	}

	@Override
	public HttpVersion getProtocol() {
		return response.getProtocol();
	}

	@Override
	public void close() {
		response.close();
	}
}
