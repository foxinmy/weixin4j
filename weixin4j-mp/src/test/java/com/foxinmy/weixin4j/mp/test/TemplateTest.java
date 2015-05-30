package com.foxinmy.weixin4j.mp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.mp.api.TmplApi;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.mp.type.IndustryType;

public class TemplateTest extends TokenTest {
	private TmplApi tmplApi;

	@Before
	public void init() {
		this.tmplApi = new TmplApi(tokenHolder);
	}

	@Test
	public void setIndustry() throws WeixinException {
		System.out.println(tmplApi
				.setTmplIndustry(IndustryType.ITKEJI_DIANZIJISHU));
	}

	@Test
	public void getid() throws WeixinException {
		System.out.println(tmplApi.getTemplateId("OPENTM201490080"));
	}

	@Test
	public void test() throws WeixinException {
		TemplateMessage tplMessage = new TemplateMessage("touser",
				"template_id", "title", "url");
		tplMessage.pushData("name", "val");
		JsonResult result = tmplApi.sendTmplMessage(tplMessage);
		Assert.assertEquals(0, result.getCode());
	}
}
