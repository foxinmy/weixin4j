package com.foxinmy.weixin4j.mp.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.HelperApi;

public class HelpTest extends TokenTest {
	private HelperApi helperApi;

	@Before
	public void init() {
		helperApi = new HelperApi(tokenManager);
	}

	@Test
	public void getWechatServerIp() throws WeixinException {
		List<String> ipList = helperApi.getWechatServerIp();
		Assert.assertFalse(ipList.isEmpty());
	}

	@Test
	public void getMenuSetting() throws WeixinException {
		System.err.println(helperApi.getMenuSetting());
	}

	@Test
	public void getAutoReplySetting() throws WeixinException {
		System.err.println(helperApi.getAutoReplySetting());
	}
}
