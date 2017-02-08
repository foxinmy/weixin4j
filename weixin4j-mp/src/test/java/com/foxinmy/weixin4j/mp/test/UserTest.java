package com.foxinmy.weixin4j.mp.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.mp.api.UserApi;
import com.foxinmy.weixin4j.mp.model.User;

/**
 * 用户相关测试
 * 
 * @className UserTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class UserTest extends TokenTest {
	private UserApi userApi;

	@Before
	public void init() {
		userApi = new UserApi(tokenManager);
	}

	@Test
	public void getUser() throws WeixinException {
		User user = userApi.getUser("owGBftyJWx21nqKlWpI3po5K_Q9o");
		Assert.assertNotNull(user);
		System.out.println(user);
		following();
	}

	@Test
	public void following() throws WeixinException {
		List<User> userList = userApi.getAllFollowing();
		for (User user : userList) {
			System.out.println(user);
		}
		Assert.assertTrue(!userList.isEmpty());
	}

	@Test
	public void remark() throws WeixinException {
		ApiResult result = userApi.remarkUserName(
				"owGBft_vbBbOaQOmpEUE4xDLeRSU", "foo");
		Assert.assertEquals("0", result.getReturnCode());
	}
}
