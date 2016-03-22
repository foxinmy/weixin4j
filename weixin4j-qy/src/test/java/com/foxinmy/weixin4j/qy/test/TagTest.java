package com.foxinmy.weixin4j.qy.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.qy.api.TagApi;
import com.foxinmy.weixin4j.qy.model.Contacts;
import com.foxinmy.weixin4j.qy.model.IdParameter;
import com.foxinmy.weixin4j.qy.model.Tag;

/**
 * 标签API测试
 * 
 * @className TagTest
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.6
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
		Contacts contacts = tagApi.getTagUsers(1);
		System.out.println(contacts);
	}

	@Test
	public void addUsers() throws WeixinException {
		IdParameter result = tagApi
				.addTagUsers(1, Arrays.asList("jinyu"), null);
		Assert.assertTrue(result.getUserIds().isEmpty());
	}

	@Test
	public void deleteUsers() throws WeixinException {
		IdParameter result = tagApi.deleteTagUsers(1, Arrays.asList("jinyu"),
				null);
		Assert.assertTrue(result.getUserIds().isEmpty());
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
