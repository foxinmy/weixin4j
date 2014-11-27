package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.Group;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 分组相关API
 * 
 * @className GroupApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see <a href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%
 *      BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3">分组接口</a>
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
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E5.88.9B.E5.BB.BA.E5.88.86.E7.BB.84">创建分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.model.Group#toCreateJson()
	 */
	public Group createGroup(String name) throws WeixinException {
		String group_create_uri = getRequestUri("group_create_uri");
		Token token = tokenHolder.getToken();
		Group group = new Group(name);
		Response response = request.post(
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
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E6.9F.A5.E8.AF.A2.E6.89.80.E6.9C.89.E5.88.86.E7.BB.84">查询所有分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public List<Group> getGroups() throws WeixinException {
		String group_get_uri = getRequestUri("group_get_uri");
		Token token = tokenHolder.getToken();
		Response response = request.get(String.format(group_get_uri,
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
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E6.9F.A5.E8.AF.A2.E7.94.A8.E6.88.B7.E6.89.80.E5.9C.A8.E5.88.86.E7.BB.84">查询用户所在分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public int getGroupByOpenId(String openId) throws WeixinException {
		String group_getid_uri = getRequestUri("group_getid_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(
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
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E4.BF.AE.E6.94.B9.E5.88.86.E7.BB.84.E5.90.8D">修改分组名</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 * @see com.foxinmy.weixin4j.mp.model.Group#toModifyJson()
	 */
	public JsonResult modifyGroup(int groupId, String name)
			throws WeixinException {
		String group_modify_uri = getRequestUri("group_modify_uri");
		Token token = tokenHolder.getToken();
		Group group = new Group(groupId, name);

		Response response = request.post(
				String.format(group_modify_uri, token.getAccessToken()),
				group.toModifyJson());
		return response.getAsJsonResult();
	}

	/**
	 * 移动分组
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @param groupId
	 *            组ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%88%86%E7%BB%84%E7%AE%A1%E7%90%86%E6%8E%A5%E5%8F%A3#.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84">移动分组</a>
	 * @see com.foxinmy.weixin4j.mp.model.Group
	 */
	public JsonResult moveGroup(String openId, int groupId)
			throws WeixinException {
		String group_move_uri = getRequestUri("group_move_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(String.format(group_move_uri,
				token.getAccessToken()), String.format(
				"{\"openid\":\"%s\",\"to_groupid\":%d}", openId, groupId));

		return response.getAsJsonResult();
	}
}
