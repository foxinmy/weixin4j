package com.foxinmy.weixin4j.qy.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.qy.api.HelperApi;

/**
 * 辅助API测试
 * 
 * @className HelperTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年12月28日
 * @since JDK 1.6
 * @see
 */
public class HelperTest extends TokenTest {
	public HelperApi helperApi;

	@Before
	public void init() {
		this.helperApi = new HelperApi(tokenHolder);
	}

	@Test
	public void backip() throws WeixinException {
		List<String> ips = helperApi.getWechatServerIp();
		Assert.assertTrue(ips != null && !ips.isEmpty());
		System.out.println(ips);
	}
}
