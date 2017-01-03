package com.foxinmy.weixin4j.mp.test;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.TmplApi;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.mp.type.IndustryType;

public class TemplateTest extends TokenTest {
	private TmplApi tmplApi;

	@Before
	public void init() {
		this.tmplApi = new TmplApi(tokenManager);
	}

	@Test
	public void setIndustry() throws WeixinException {
		System.out.println(tmplApi
				.setTmplIndustry(IndustryType.ITKEJI_DIANZIJISHU));
	}

	@Test
	public void getId() throws WeixinException {
		System.out.println(tmplApi.getTemplateId("OPENTM201490080"));
	}

	@Test
	public void getAll() throws WeixinException {
		System.out.println(tmplApi.getAllTemplates());
	}

	@Test
	public void test() throws WeixinException {
		TemplateMessage tplMessage = new TemplateMessage("touser",
				"template_id", "url");
		tplMessage.pushHead("head").pushTail("tail").pushItem("key1", "text1");
		String result = tmplApi.sendTmplMessage(tplMessage);
		System.out.println(result);
	}
}
