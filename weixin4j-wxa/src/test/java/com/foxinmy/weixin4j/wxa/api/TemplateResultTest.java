package com.foxinmy.weixin4j.wxa.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.util.IOUtil;

public class TemplateResultTest {

	@Test
	public void test() throws IOException {
		TemplateResult t = readTemplate();
		assertEquals(0, t.getErrCode());
		assertEquals("ok", t.getErrMsg());
		assertEquals("AT0002", t.getId());
		assertEquals("购买成功通知", t.getTitle());
		assertEquals(3, t.getKeywords().size());
		assertEquals(3, t.getKeywords().get(0).getId());
		assertEquals("购买地点", t.getKeywords().get(0).getName());
		assertEquals("TIT造舰厂", t.getKeywords().get(0).getExample());
	}

	private TemplateResult readTemplate() throws IOException {
		InputStream inputStream = getClass().getResourceAsStream("Template.json");
		try {
			TemplateResult t = JSON.parseObject(inputStream, TemplateResult.class);
			return t;
		} finally {
			IOUtil.close(inputStream);
		}
	}

}
