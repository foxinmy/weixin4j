package com.foxinmy.weixin4j.wxa.api;

import com.alibaba.fastjson.TypeReference;

class WxaApiAsyncResult extends WxaApiResult {

	private static final long serialVersionUID = 2020022001L;

	public static final TypeReference<WxaApiAsyncResult> TYPE_REFERENCE
		= new TypeReference<WxaApiAsyncResult>() {
		};

	private String traceId;

	public WxaApiAsyncResult() {
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

}
