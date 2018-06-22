package com.foxinmy.weixin4j.wxa.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class AddTemplateParameterTest {

	@Test
	public void test() {
		AddTemplateParameter param = new AddTemplateParameter("AT0002", new int[] { 3, 4, 5 });
		String json = JSON.toJSONString(param);
		assertEquals("{\"id\":\"AT0002\",\"keyword_id_list\":[3,4,5]}", json);
	}

}
