package com.foxinmy.weixin4j.mp.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.HelperApi;

/**
 * 辅助API测试
 *
 * @className HelperTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年5月24日
 * @since JDK 1.6
 * @see
 */
public class HelperTest extends TokenTest {
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

	@Test
	public void clearQuota() throws WeixinException {
		System.err.println(helperApi.clearQuota(weixinAccount.getId()));
	}
}
