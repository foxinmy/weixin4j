package com.foxinmy.weixin4j.mp.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.BaseResult;
import com.foxinmy.weixin4j.mp.api.UserApi;
import com.foxinmy.weixin4j.mp.model.User;

/**
 * 用户相关测试
 * 
 * @className UserTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class UserTest extends TokenTest {
	private UserApi userApi;

	@Before
	public void init() {
		userApi = new UserApi(tokenApi);
	}

	@Test
	public void getUser() throws WeixinException {
		User user = userApi.getUser("owGBft_vbBbOaQOmpEUE4xDLeRSU");
		Assert.assertNotNull(user);
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
		BaseResult result = userApi.remarkUserName(
				"owGBft_vbBbOaQOmpEUE4xDLeRSU", "foo");
		Assert.assertEquals(0, result.getErrcode());
	}
}
