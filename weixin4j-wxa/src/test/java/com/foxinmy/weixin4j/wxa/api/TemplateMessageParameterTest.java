package com.foxinmy.weixin4j.wxa.api;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.wxa.api.TemplateMessageParameter.TemplateMessageData;

public class TemplateMessageParameterTest {

	@Test
	public void test() {
		TemplateMessageParameter param = new TemplateMessageParameter();
		param.setToUser("OPENID");
		param.setTemplateId("TEMPLATE_ID");
		param.setPage("index");
		param.setFormId("FORMID");
		Map<String, TemplateMessageData> data = new LinkedHashMap<String, TemplateMessageData>();
		data.put("keyword1", new TemplateMessageData("339208499"));
		data.put("keyword2", new TemplateMessageData("2015年01月05日 12:30"));
		data.put("keyword3", new TemplateMessageData("粤海喜来登酒店"));
		data.put("keyword4", new TemplateMessageData("广州市天河区天河路208号"));
		param.setData(data);
		param.setEmphasisKeyword("keyword1.DATA");
		String json = JSON.toJSONString(param);
		System.out.println(json);
		assertTrue(json.contains("\"touser\":"));
		assertTrue(json.contains("\"template_id\":"));
		assertTrue(json.contains("\"page\":"));
		assertTrue(json.contains("\"form_id\":"));
		assertTrue(json.contains("\"data\":{\"keyword1\":{\"value\":\"339208499\"},\"keyword2\":{\"value\":\"2015年01月05日 12:30\"},\"keyword3\":{\"value\":\"粤海喜来登酒店\"},\"keyword4\":{\"value\":\"广州市天河区天河路208号\"}}"));
		assertTrue(json.contains("\"emphasis_keyword\":"));
	}

}
