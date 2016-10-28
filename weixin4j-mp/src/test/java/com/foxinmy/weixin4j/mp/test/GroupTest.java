package com.foxinmy.weixin4j.mp.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.mp.api.GroupApi;
import com.foxinmy.weixin4j.mp.model.Group;

/**
 * 用户分组测试
 * 
 * @className GroupTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class GroupTest extends TokenTest {
	private GroupApi groupApi;

	@Before
	public void init() {
		groupApi = new GroupApi(tokenManager);
	}

	@Test
	public void create() throws WeixinException {
		Group group = groupApi.createGroup("my");
		Assert.assertNotNull(group);
	}

	@Test
	public void get() throws WeixinException {
		List<Group> groups = groupApi.getGroups();
		System.err.println(groups);
		Assert.assertTrue(groups.size() > 0);
	}

	@Test
	public void getid() throws WeixinException {
		int gid = groupApi.getGroupByOpenId("owGBft_vbBbOaQOmpEUE4xDLeRSU");
		Assert.assertTrue(gid >= 0);
	}

	@Test
	public void modify() throws WeixinException {
		ApiResult result = groupApi.modifyGroup(100, "my1");
		Assert.assertEquals("0", result.getReturnCode());
	}

	@Test
	public void move() throws WeixinException {
		ApiResult result = groupApi.moveGroup(100,
				"owGBft_vbBbOaQOmpEUE4xDLeRSU");
		Assert.assertEquals("0", result.getReturnCode());
	}

	@Test
	public void batchMove() throws WeixinException {
		ApiResult result = groupApi.moveGroup(100,
				"owGBft_vbBbOaQOmpEUE4xDLeRSU");
		Assert.assertEquals("0", result.getReturnCode());
	}

	@Test
	public void delete() throws WeixinException {
		ApiResult result = groupApi.deleteGroup(100);
		Assert.assertEquals("0", result.getReturnCode());
	}
}
