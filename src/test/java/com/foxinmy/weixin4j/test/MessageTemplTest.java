package com.foxinmy.weixin4j.test;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.msg.out.TemplateMessage;

public class MessageTemplTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		this.weixinProxy = new WeixinProxy();
	}

	@Test
	public void test() {
		TemplateMessage tplMessage = new TemplateMessage("touser",
				"template_id", "url");
		tplMessage.pushData("name", "fox");
		System.out.print(tplMessage.toJson());
		try {
			System.out.print(weixinProxy.sendTplMessage(tplMessage));
		} catch (WeixinException e) {
			System.out.println(e.getErrorMsg());
		}
	}
}
