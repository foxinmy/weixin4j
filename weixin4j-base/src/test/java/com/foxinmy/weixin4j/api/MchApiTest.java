package com.foxinmy.weixin4j.api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.foxinmy.weixin4j.model.WeixinPayAccount;

public class MchApiTest {

	@Test
	public void testWeixinBundle() {
		MchApi mchApi = new MchApi(new WeixinPayAccount(null, null, null));
		assertNotNull(mchApi.weixinBundle());
	}

}
