package com.foxinmy.weixin4j.mp.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.mp.api.TagApi;
import com.foxinmy.weixin4j.mp.model.Tag;
import com.foxinmy.weixin4j.mp.model.User;

/**
 * 标签单元测试
 * 
 * @className TagTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年5月2日
 * @since JDK 1.6
 * @see
 */
public class TagTest extends TokenTest {
	private TagApi tagApi;

	@Before
	public void init() {
		tagApi = new TagApi(tokenManager);
	}

	@Test
	public void create() throws WeixinException {
		Tag tag = tagApi.createTag("测试三");
		Assert.assertNotNull(tag);
		System.out.println(tag);
	}

	@Test
	public void list() throws WeixinException {
		List<Tag> tags = tagApi.listTags();
		Assert.assertFalse(tags.isEmpty());
	}

	@Test
	public void update() throws WeixinException {
		ApiResult result = tagApi.updateTag(new Tag(120, "测试12"));
		System.err.println(result);
	}

	@Test
	public void remove() throws WeixinException {
		ApiResult result = tagApi.deleteTag(134);
		System.err.print(result);
	}

	@Test
	public void batchtagging() throws WeixinException {
		ApiResult result = tagApi.taggingUsers(120,
				"owGBft-GyGJuKXBzpkzrfl-RG8TI", "owGBfty5TYNwh-3iUTGtxAHcD310",
				"owGBftzXEfBml_bYvbrYxE5lE5U8");
		System.err.println(result);
	}

	@Test
	public void batchuntagging() throws WeixinException {
		ApiResult result = tagApi.taggingUsers(120,
				"owGBftwS5Yr6xKH_Hb9mGv1nbd3o");
		System.err.println(result);
	}

	@Test
	public void getidlist() throws WeixinException {
		Integer[] tagIds = tagApi.getUserTags("owGBft-GyGJuKXBzpkzrfl-RG8TI");
		Assert.assertNotNull(tagIds);
		System.out.println(tagIds[0]);
	}

	@Test
	public void getAllTagFollowing() throws WeixinException {
		List<User> users = tagApi.getAllTagFollowing(120);
		Assert.assertNotNull(users);
		System.out.println(users);
	}

	@Test
	public void getAllTagFollowingOpenIds() throws WeixinException {
		List<String> tags = tagApi.getAllTagFollowingOpenIds(120);
		Assert.assertNotNull(tags);
		System.out.println(tags);
	}
}
