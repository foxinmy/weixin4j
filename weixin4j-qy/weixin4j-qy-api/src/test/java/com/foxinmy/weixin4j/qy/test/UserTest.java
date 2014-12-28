package com.foxinmy.weixin4j.qy.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.qy.api.UserApi;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.type.UserStatus;

/**
 * 部门API测试
 * 
 * @className DepartTest
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see
 */
public class UserTest extends TokenTest {
	public UserApi userApi;

	@Before
	public void init() {
		this.userApi = new UserApi(tokenHolder);
	}

	@Test
	public void create() throws WeixinException {
		User user = new User("u001", "jack");
		user.setMobile("13500000000");
		user.setDepartment(1);
		user.pushExattr("爱好", "code");
		JsonResult result = userApi.createUser(user);
		Assert.assertEquals("created", result.getDesc());
	}

	@Test
	public void update() throws WeixinException {
		User user = new User("u001", "ted");
		user.setMobile("13500000000");
		user.setDepartment(1);
		user.pushExattr("爱好", "code");
		JsonResult result = userApi.updateUser(user);
		Assert.assertEquals("updated", result.getDesc());
	}

	@Test
	public void get() throws WeixinException {
		User user = userApi.getUser("u001");
		Assert.assertTrue(user != null);
		System.out.println(user);
	}

	@Test
	public void list() throws WeixinException {
		List<User> userList = userApi.listUser(1, true, UserStatus.BOTH, true);
		Assert.assertFalse(userList.isEmpty());
		System.out.println(userList);
		userList = userApi.listUser(1);
		Assert.assertFalse(userList.isEmpty());
		System.out.println(userList);
	}

	@Test
	public void delete() throws WeixinException {
		JsonResult result = userApi.deleteUser("u001");
		Assert.assertEquals("deleted", result.getDesc());
	}
}
