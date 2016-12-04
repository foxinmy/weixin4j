package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.Group;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 分组相关API
 * 
 * @className GroupApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.mp.model.Group
 */
@Deprecated
public class GroupApi extends MpApi {

	private final TokenManager tokenManager;

	public GroupApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 创建分组
	 * 
	 * @param name
	 *            组名称
	 * @return group对象
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">创建分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.model.Group#toCreateJson()
	 */
	public Group createGroup(String name) throws WeixinException {
		String group_create_uri = getRequestUri("group_create_uri");
		Token token = tokenManager.getCache();
		Group group = new Group(name);
		WeixinResponse response = weixinExecutor.post(
				String.format(group_create_uri, token.getAccessToken()),
				group.toCreateJson());
		return JSON.parseObject(response.getAsJson().getString("group"),
				Group.class);
	}

	/**
	 * 查询所有分组
	 * 
	 * @return 组集合
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">查询所有分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public List<Group> getGroups() throws WeixinException {
		String group_get_uri = getRequestUri("group_get_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				group_get_uri, token.getAccessToken()));

		return JSON.parseArray(response.getAsJson().getString("groups"),
				Group.class);
	}

	/**
	 * 查询用户所在分组
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @return 组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">查询用户所在分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public int getGroupByOpenId(String openId) throws WeixinException {
		String group_getid_uri = getRequestUri("group_getid_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(group_getid_uri, token.getAccessToken()),
				String.format("{\"openid\":\"%s\"}", openId));

		return response.getAsJson().getIntValue("groupid");
	}

	/**
	 * 修改分组名
	 * 
	 * @param groupId
	 *            组ID
	 * @param name
	 *            组名称
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">修改分组名</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.model.Group#toModifyJson()
	 */
	public ApiResult modifyGroup(int groupId, String name)
			throws WeixinException {
		String group_modify_uri = getRequestUri("group_modify_uri");
		Token token = tokenManager.getCache();
		Group group = new Group(groupId, name);

		WeixinResponse response = weixinExecutor.post(
				String.format(group_modify_uri, token.getAccessToken()),
				group.toModifyJson());
		return response.getAsResult();
	}

	/**
	 * 移动用户到分组
	 * 
	 * @param groupId
	 *            组ID
	 * @param openId
	 *            用户对应的ID
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public ApiResult moveGroup(int groupId, String openId)
			throws WeixinException {
		String group_move_uri = getRequestUri("group_move_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(String.format(
				group_move_uri, token.getAccessToken()), String.format(
				"{\"openid\":\"%s\",\"to_groupid\":%d}", openId, groupId));

		return response.getAsResult();
	}

	/**
	 * 批量移动分组
	 * 
	 * @param groupId
	 *            组ID
	 * @param openIds
	 *            用户ID列表(不能超过50个)
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">批量移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public ApiResult moveGroup(int groupId, String... openIds)
			throws WeixinException {
		String group_batchmove_uri = getRequestUri("group_batchmove_uri");
		Token token = tokenManager.getCache();
		JSONObject obj = new JSONObject();
		obj.put("to_groupid", groupId);
		obj.put("openid_list", openIds);
		WeixinResponse response = weixinExecutor.post(
				String.format(group_batchmove_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsResult();
	}

	/**
	 * 删除用户分组,所有该分组内的用户自动进入默认分组.
	 * 
	 * @param groupId
	 *            组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">删除用户分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public ApiResult deleteGroup(int groupId) throws WeixinException {
		String group_delete_uri = getRequestUri("group_delete_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(group_delete_uri, token.getAccessToken()),
				String.format("{\"group\":{\"id\":%d}}", groupId));

		return response.getAsResult();
	}
}
