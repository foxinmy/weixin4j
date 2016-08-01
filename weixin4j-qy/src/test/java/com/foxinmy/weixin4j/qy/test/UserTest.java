package com.foxinmy.weixin4j.qy.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.qy.api.MediaApi;
import com.foxinmy.weixin4j.qy.api.UserApi;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.type.UserStatus;

/**
 * 用户API测试
 * 
 * @className UserTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月18日
 * @since JDK 1.6
 * @see
 */
public class UserTest extends TokenTest {
	public UserApi userApi;
	public MediaApi mediaApi;

	@Before
	public void init() {
		this.userApi = new UserApi(tokenManager);
		this.mediaApi = new MediaApi(tokenManager);
	}

	@Test
	public void create() throws WeixinException {
		User user = new User("id", "name");
		user.setPartyIds(1);
		user.pushExattr("爱好", "code");
		ApiResult result = userApi.createUser(user);
		Assert.assertEquals("created", result.getReturnMsg());
	}

	@Test
	public void batchUpload() throws WeixinException {
		User user = new User("id", "name");
		user.setPartyIds(1);
		String mediaId = mediaApi.batchUploadUsers(Arrays.asList(user));
		System.err.println(mediaId);
	}

	@Test
	public void update() throws WeixinException {
		User user = new User("id", "name");
		user.setPartyIds(1);
		user.pushExattr("爱好", "code");
		ApiResult result = userApi.updateUser(user);
		Assert.assertEquals("updated", result.getReturnMsg());
	}

	@Test
	public void get() throws WeixinException {
		User user = userApi.getUser("jinyu");
		Assert.assertTrue(user != null);
		System.out.println(user);
	}

	@Test
	public void list() throws WeixinException {
		List<User> userList = userApi.listUser(1, true, UserStatus.BOTH, true);
		Assert.assertFalse(userList.isEmpty());
		System.out.println(userList);
	}

	@Test
	public void delete() throws WeixinException {
		ApiResult result = userApi.deleteUser("u001");
		Assert.assertEquals("deleted", result.getReturnMsg());
	}

	@Test
	public void invite() throws WeixinException {
		userApi.inviteUser("11", null);
	}

	@Test
	public void convert() throws WeixinException {
		String[] result = userApi.userid2openid("jinyu", 1);
		System.err.println(userApi.openid2userid(result[0]));
	}
}
