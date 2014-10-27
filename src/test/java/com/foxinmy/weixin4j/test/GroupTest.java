package com.foxinmy.weixin4j.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.api.GroupApi;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.BaseResult;
import com.foxinmy.weixin4j.model.Group;

/**
 * 用户分组测试
 * 
 * @className GroupTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class GroupTest extends TokenTest {
	private GroupApi groupApi;

	@Before
	public void init() {
		groupApi = new GroupApi(tokenApi);
	}

	@Test
	public void create() throws WeixinException {
		Group group = groupApi.createGroup("my");
		Assert.assertNotNull(group);
	}

	@Test
	public void get() throws WeixinException {
		List<Group> groups = groupApi.getGroups();
		for (Group group : groups) {
			System.out.println(group);
		}
		Assert.assertEquals(1, groups.size());
	}

	@Test
	public void getid() throws WeixinException {
		int gid = groupApi.getGroupByOpenId("owGBft_vbBbOaQOmpEUE4xDLeRSU");
		Assert.assertTrue(gid >= 0);
	}

	@Test
	public void modify() throws WeixinException {
		BaseResult result = groupApi.modifyGroup(100, "my1");
		Assert.assertEquals(0, result.getErrcode());
	}

	@Test
	public void move() throws WeixinException {
		BaseResult result = groupApi.moveGroup("owGBft_vbBbOaQOmpEUE4xDLeRSU",
				100);
		Assert.assertEquals(0, result.getErrcode());
	}
}
