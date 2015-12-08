package com.foxinmy.weixin4j.mp.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.mp.api.UserApi;
import com.foxinmy.weixin4j.mp.model.User;

/**
 * 用户相关测试
 * 
 * @className UserTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class UserTest extends TokenTest {
	private UserApi userApi;

	@Before
	public void init() {
		userApi = new UserApi(tokenHolder);
	}

	@Test
	public void getUser() throws WeixinException {
		User user = userApi.getUser("o9Onds6fbeK0lDwD8lJt2PY1VRys");
		Assert.assertNotNull(user);
		System.out.println(user);
		// following();
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
		JsonResult result = userApi.remarkUserName(
				"owGBft_vbBbOaQOmpEUE4xDLeRSU", "foo");
		Assert.assertEquals(0, result.getCode());
	}
}
