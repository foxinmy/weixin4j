package com.foxinmy.weixin4j.wxa.api;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class WxaCodeParameterTest {

	@Test
	public void test() {
		WxaCodeParameter param = new WxaCodeParameter("pages/index/index", 430, false, new Color(1, 2, 3), true);
		String json = JSON.toJSONString(param);
		assertTrue(json.contains("\"line_color\":"));
		assertTrue(json.contains("\"is_hyaline\":true"));
	}

}
