package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.Group;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 分组相关API
 * 
 * @className GroupApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html">分组接口</a>
 * @see com.foxinmy.weixin4j.mp.model.Group
 */
public class GroupApi extends MpApi {

	private final TokenHolder tokenHolder;

	public GroupApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 创建分组
	 * 
	 * @param name
	 *            组名称
	 * @return group对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E5.88.9B.E5.BB.BA.E5.88.86.E7.BB.84">创建分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.model.Group#toCreateJson()
	 */
	public Group createGroup(String name) throws WeixinException {
		String group_create_uri = getRequestUri("group_create_uri");
		Token token = tokenHolder.getToken();
		Group group = new Group(name);
		WeixinResponse response = weixinExecutor.post(
				String.format(group_create_uri, token.getAccessToken()),
				group.toCreateJson());

		return response.getAsJson().getObject("group", Group.class);
	}

	/**
	 * 查询所有分组
	 * 
	 * @return 组集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E6.9F.A5.E8.AF.A2.E6.89.80.E6.9C.89.E5.88.86.E7.BB.844">查询所有分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public List<Group> getGroups() throws WeixinException {
		String group_get_uri = getRequestUri("group_get_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(group_get_uri,
				token.getAccessToken()));

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
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E6.9F.A5.E8.AF.A2.E7.94.A8.E6.88.B7.E6.89.80.E5.9C.A8.E5.88.86.E7.BB.84">查询用户所在分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public int getGroupByOpenId(String openId) throws WeixinException {
		String group_getid_uri = getRequestUri("group_getid_uri");
		Token token = tokenHolder.getToken();
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
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E4.BF.AE.E6.94.B9.E5.88.86.E7.BB.84.E5.90.8D">修改分组名</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.model.Group#toModifyJson()
	 */
	public JsonResult modifyGroup(int groupId, String name)
			throws WeixinException {
		String group_modify_uri = getRequestUri("group_modify_uri");
		Token token = tokenHolder.getToken();
		Group group = new Group(groupId, name);

		WeixinResponse response = weixinExecutor.post(
				String.format(group_modify_uri, token.getAccessToken()),
				group.toModifyJson());
		return response.getAsJsonResult();
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
	 *      href="http://mp.weixin.qq.com/wiki/13/be5272dc4930300ba561d927aead2569.html#.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84">移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public JsonResult moveGroup(int groupId, String openId)
			throws WeixinException {
		String group_move_uri = getRequestUri("group_move_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(String.format(group_move_uri,
				token.getAccessToken()), String.format(
				"{\"openid\":\"%s\",\"to_groupid\":%d}", openId, groupId));

		return response.getAsJsonResult();
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
	 *      href="http://mp.weixin.qq.com/wiki/0/56d992c605a97245eb7e617854b169fc.html#.E6.89.B9.E9.87.8F.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84">批量移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public JsonResult moveGroup(int groupId, String... openIds)
			throws WeixinException {
		String group_batchmove_uri = getRequestUri("group_batchmove_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("to_groupid", groupId);
		obj.put("openid_list", openIds);
		WeixinResponse response = weixinExecutor.post(
				String.format(group_batchmove_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJsonResult();
	}

	/**
	 * 删除用户分组,所有该分组内的用户自动进入默认分组.
	 * 
	 * @param groupId
	 *            组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/56d992c605a97245eb7e617854b169fc.html#.E5.88.A0.E9.99.A4.E5.88.86.E7.BB.84">删除用户分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public JsonResult deleteGroup(int groupId) throws WeixinException {
		String group_delete_uri = getRequestUri("group_delete_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(group_delete_uri, token.getAccessToken()),
				String.format("{\"group\":{\"id\":%d}}", groupId));

		return response.getAsJsonResult();
	}
}
