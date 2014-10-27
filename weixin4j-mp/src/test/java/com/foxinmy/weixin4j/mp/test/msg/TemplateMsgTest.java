package com.foxinmy.weixin4j.mp.test.msg;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.BaseResult;
import com.foxinmy.weixin4j.mp.api.TmplApi;
import com.foxinmy.weixin4j.mp.response.TemplateMessage;
import com.foxinmy.weixin4j.mp.test.TokenTest;

public class TemplateMsgTest extends TokenTest {
	private TmplApi tmplApi;

	@Before
	public void init() {
		this.tmplApi = new TmplApi(tokenApi);
	}

	@Test
	public void test() throws WeixinException {
		TemplateMessage tplMessage = new TemplateMessage("touser",
				"template_id", "title", "url");
		tplMessage.pushData("name", "val");
		BaseResult result = tmplApi.sendTmplMessage(tplMessage);
		Assert.assertEquals(0, result.getErrcode());
	}
}
