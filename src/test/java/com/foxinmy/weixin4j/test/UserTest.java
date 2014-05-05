package com.foxinmy.weixin4j.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.User;

/**
 * 用户相关测试
 * @className UserTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class UserTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		weixinProxy = new WeixinProxy();
	}

	@Test
	public void getUser() {
		try {
			System.out.println(weixinProxy.getUser("owGBft_vbBbOaQOmpEUE4xDLeRSU"));
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void following() {
		try {
			System.out.println(weixinProxy.getFollowing(null));
			List<User> userList = weixinProxy.getAllFollowing();
			for (User user : userList) {
				System.out.println(user);
			}
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
}
