package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.mp.model.Following;
import com.foxinmy.weixin4j.mp.model.Tag;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 标签相关API
 * 
 * @className TagApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年4月29日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.mp.model.Tag
 */
public class TagApi extends MpApi {
	private final TokenManager tokenManager;
	private final UserApi userApi;

	public TagApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
		this.userApi = new UserApi(tokenManager);
	}

	/**
	 * 创建标签
	 * 
	 * @param name
	 *            标签名（30个字符以内）
	 * @return 标签对象
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.Tag
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">创建标签</a>
	 */
	public Tag createTag(String name) throws WeixinException {
		String tag_create_uri = getRequestUri("tag_create_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_create_uri, tokenManager.getAccessToken()),
				String.format("{\"tag\":{\"name\":\"%s\"}}", name));

		return JSON.parseObject(response.getAsJson().getString("tag"),
				Tag.class);
	}

	/**
	 * 获取标签
	 * 
	 * @return 标签列表
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.Tag
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">获取标签</a>
	 */
	public List<Tag> listTags() throws WeixinException {
		String tag_get_uri = getRequestUri("tag_get_uri");
		WeixinResponse response = weixinExecutor.get(String.format(tag_get_uri,
				tokenManager.getAccessToken()));

		return JSON.parseArray(response.getAsJson().getString("tags"),
				Tag.class);
	}

	/**
	 * 更新标签
	 * 
	 * @param tag
	 *            标签对象
	 * @return 操作结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.Tag
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">更新标签</a>
	 */
	public JsonResult updateTag(Tag tag) throws WeixinException {
		String tag_update_uri = getRequestUri("tag_update_uri");
		JSONObject obj = new JSONObject();
		obj.put("tag", tag);
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_update_uri, tokenManager.getAccessToken()),
				obj.toJSONString());
		return response.getAsJsonResult();
	}

	/**
	 * 删除标签
	 * 
	 * @param tagId
	 *            标签id
	 * @return 操作结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">删除标签</a>
	 */
	public JsonResult deleteTag(int tagId) throws WeixinException {
		String tag_delete_uri = getRequestUri("tag_delete_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_delete_uri, tokenManager.getAccessToken()),
				String.format("{\"tagid\":%d}", tagId));
		return response.getAsJsonResult();
	}

	/**
	 * 批量为用户打标签:标签功能目前支持公众号为用户打上最多三个标签
	 * 
	 * @param tagId
	 *            标签ID
	 * @param openIds
	 *            用户ID
	 * @return 操作结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">批量为用户打标签</a>
	 */
	public JsonResult taggingUsers(int tagId, String... openIds)
			throws WeixinException {
		return batchUsers("tag_tagging_uri", tagId, openIds);
	}

	private JsonResult batchUsers(String batchType, int tagId,
			String... openIds) throws WeixinException {
		String tag_batch_uri = getRequestUri(batchType);
		JSONObject obj = new JSONObject();
		obj.put("openid_list", openIds);
		obj.put("tagid", tagId);
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_batch_uri, tokenManager.getAccessToken()),
				obj.toJSONString());
		return response.getAsJsonResult();
	}

	/**
	 * 批量为用户取消标签
	 * 
	 * @param tagId
	 *            标签ID
	 * @param openIds
	 *            用户ID
	 * @return 操作结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">批量为用户取消标签</a>
	 */
	public JsonResult untaggingUsers(int tagId, String... openIds)
			throws WeixinException {
		return batchUsers("tag_untagging_uri", tagId, openIds);
	}

	/**
	 * 获取标签下粉丝列表
	 * 
	 * @param tagId
	 *            标签ID
	 * @param nextOpenId
	 *            第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 用户openid列表
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">获取标签下粉丝列表</a>
	 */
	public Following getTagFollowingOpenIds(int tagId, String nextOpenId)
			throws WeixinException {
		String tag_user_uri = getRequestUri("tag_user_uri");
		JSONObject obj = new JSONObject();
		obj.put("tagid", tagId);
		obj.put("next_openid", nextOpenId);
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_user_uri, tokenManager.getAccessToken()),
				obj.toJSONString());

		JSONObject result = response.getAsJson();
		Following following = JSON.toJavaObject(result, Following.class);

		if (following.getCount() > 0) {
			following.setOpenIds(JSON.parseArray(result.getJSONObject("data")
					.getString("openid"), String.class));
		}
		return following;
	}

	/**
	 * 获取标签下粉丝列表 <font corlor="red">请慎重使用</font>
	 * 
	 * @param tagId
	 *            标签ID
	 * @param nextOpenId
	 *            第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 被打标签者信息 <font color="red">包含用户的详细信息</font>
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">获取标签下粉丝列表</a>
	 */
	public Following getTagFollowing(int tagId, String nextOpenId)
			throws WeixinException {
		Following following = getTagFollowingOpenIds(tagId, nextOpenId);
		if (following.getCount() > 0) {
			List<User> users = new ArrayList<User>(following.getCount());
			for (int i = 1; i <= (int) Math.ceil(following.getCount() / 100d); i++) {
				users.addAll(userApi.getUsers(following
						.getOpenIds()
						.subList((i - 1) * 100,
								Math.min(i * 100, following.getCount()))
						.toArray(new String[] {})));
			}
			following.setUserList(users);
		}
		return following;
	}

	/**
	 * 获取标签下全部的粉丝列表 <font corlor="red">请慎重使用</font>
	 * 
	 * @param tagId
	 *            标签ID
	 * @return 用户openid列表
	 * @throws WeixinException
	 * @see #getTagFollowingOpenIds(int,String)
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">获取标签下粉丝列表</a>
	 */
	public List<String> getAllTagFollowingOpenIds(int tagId)
			throws WeixinException {
		List<String> openIds = new ArrayList<String>();
		String nextOpenId = null;
		Following f = null;
		for (;;) {
			f = getTagFollowingOpenIds(tagId, nextOpenId);
			if (f.getCount() == 0) {
				break;
			}
			openIds.addAll(f.getOpenIds());
			nextOpenId = f.getNextOpenId();
		}
		return openIds;
	}

	/**
	 * 获取标签下全部的粉丝列表 <font corlor="red">请慎重使用</font>
	 * 
	 * @param tagId
	 *            标签ID
	 * @return 被打标签者信息 <font color="red">包含用户的详细信息</font>
	 * @throws WeixinException
	 * @see #getTagFollowing(int,String)
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">获取标签下粉丝列表</a>
	 */
	public List<User> getAllTagFollowing(int tagId) throws WeixinException {
		List<User> userList = new ArrayList<User>();
		String nextOpenId = null;
		Following f = null;
		for (;;) {
			f = getTagFollowing(tagId, nextOpenId);
			if (f.getCount() == 0) {
				break;
			}
			userList.addAll(f.getUserList());
			nextOpenId = f.getNextOpenId();
		}
		return userList;
	}

	/**
	 * 获取用户身上的标签列表
	 * 
	 * @param openId
	 *            用户ID
	 * @return 标签ID集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">
	 *      获取用户身上的标签列表</a>
	 */
	public Integer[] getUserTags(String openId) throws WeixinException {
		String tag_userids_uri = getRequestUri("tag_userids_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_userids_uri, tokenManager.getAccessToken()),
				String.format("{\"openid\":\"%s\"}", openId));
		return response.getAsJson().getJSONArray("tagid_list")
				.toArray(new Integer[] {});
	}
}
