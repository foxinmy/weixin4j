package com.foxinmy.weixin4j.wxa.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.util.IOUtil;

public class TemplateListResultTest {

	@Test
	public void testTemplatesInLibrary() throws IOException {
		TemplateListResult r = readTemplateList("TemplateListInLibrary.json");
		assertEquals(0, r.getErrCode());
		assertEquals("ok", r.getErrMsg());
		assertEquals(599, r.getTotalCount().longValue());

		assertEquals(5, r.getList().size());

		assertEquals("AT0002", r.getList().get(0).getId());
		assertEquals("购买成功通知", r.getList().get(0).getTitle());

		assertEquals("AT0003", r.getList().get(1).getId());
		assertEquals("购买失败通知", r.getList().get(1).getTitle());
	}

	@Test
	public void testMyTemplates() throws IOException {
		TemplateListResult r = readTemplateList("TemplateList.json");
		assertEquals(0, r.getErrCode());
		assertEquals("ok", r.getErrMsg());
		assertEquals("wDYzYZVxobJivW9oMpSCpuvACOfJXQIoKUm0PY397Tc", r.getList().get(0).getId());
		assertEquals("购买成功通知", r.getList().get(0).getTitle());
		assertEquals("购买地点{{keyword1.DATA}}\n购买时间{{keyword2.DATA}}\n物品名称{{keyword3.DATA}}\n", r.getList().get(0).getContent());
		assertEquals("购买地点：TIT造舰厂\n购买时间：2016年6月6日\n物品名称：咖啡\n", r.getList().get(0).getExample());
	}

	private TemplateListResult readTemplateList(String resourceName) throws IOException {
		InputStream inputStream = getClass().getResourceAsStream(resourceName);
		try {
			TemplateListResult tl = JSON.parseObject(inputStream, TemplateListResult.class);
			return tl;
		} finally {
			IOUtil.close(inputStream);
		}
	}

}
