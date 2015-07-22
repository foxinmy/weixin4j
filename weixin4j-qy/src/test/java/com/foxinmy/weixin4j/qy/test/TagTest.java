package com.foxinmy.weixin4j.qy.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.qy.api.TagApi;
import com.foxinmy.weixin4j.qy.model.Tag;
import com.foxinmy.weixin4j.qy.model.User;

/**
 * 标签API测试
 * 
 * @className TagTest
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see
 */
public class TagTest extends TokenTest {
	public TagApi tagApi;

	@Before
	public void init() {
		this.tagApi = new TagApi(tokenHolder);
	}

	@Test
	public void create() throws WeixinException {
		int tagId = tagApi.createTag(new Tag("coder"));
		Assert.assertTrue(tagId > 0);
	}

	@Test
	public void update() throws WeixinException {
		JsonResult result = tagApi.updateTag(new Tag(1, "coder456"));
		Assert.assertEquals("updated", result.getDesc());
	}

	@Test
	public void getUsers() throws WeixinException {
		List<User> listUser = tagApi.getTagUsers(1);
		Assert.assertFalse(listUser.isEmpty());
		System.out.println(listUser);
	}

	@Test
	public void addUsers() throws WeixinException {
		JsonResult result = tagApi.addTagUsers(1, Arrays.asList("jinyu"));
		Assert.assertEquals("ok", result.getDesc());
	}

	@Test
	public void deleteUsers() throws WeixinException {
		JsonResult result = tagApi.deleteTagUsers(1, Arrays.asList("jinyu"));
		Assert.assertEquals("ok", result.getDesc());
		System.out.println(result);
	}

	@Test
	public void list() throws WeixinException {
		List<Tag> tags = tagApi.listTag();
		Assert.assertFalse(tags.isEmpty());
		System.out.println(tags);
	}

	@Test
	public void delete() throws WeixinException {
		JsonResult result = tagApi.deleteTag(3);
		Assert.assertEquals("deleted", result.getDesc());
	}
}
