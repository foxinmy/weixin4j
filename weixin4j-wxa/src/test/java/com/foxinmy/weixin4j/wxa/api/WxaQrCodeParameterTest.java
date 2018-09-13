package com.foxinmy.weixin4j.wxa.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class WxaQrCodeParameterTest {

	@Test
	public void test() {
		WxaQrCodeParameter param = new WxaQrCodeParameter("myPath", 100);
		String json = JSON.toJSONString(param);
		assertTrue(json.contains("\"path\":\"myPath\""));
		assertTrue(json.contains("\"width\":100"));
	}

}
