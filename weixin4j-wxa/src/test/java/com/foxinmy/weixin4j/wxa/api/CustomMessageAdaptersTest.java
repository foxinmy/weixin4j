package com.foxinmy.weixin4j.wxa.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.wxa.model.custommessage.CustomMessage;
import com.foxinmy.weixin4j.wxa.model.custommessage.Text;

public class CustomMessageAdaptersTest {

	@Test
	public void testToMap() {
		CustomMessage customMessage = new CustomMessage();
		customMessage.setToUser("OPENID");
		customMessage.setTuple(new Text("Hello World"));

		String json = JSON.toJSONString(CustomMessageAdapters.toMap(customMessage));
		System.out.println(json);
		assertEquals("{\"touser\":\"OPENID\",\"text\":{\"content\":\"Hello World\"},\"msgtype\":\"text\"}", json);
	}

}
