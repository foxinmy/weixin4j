package com.foxinmy.weixin4j.qy.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.type.InviteType;
import com.foxinmy.weixin4j.qy.type.UserStatus;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 成员API
 * 
 * @className UserApi
 * @author jy
 * @date 2014年11月19日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.qy.model.User
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98">管理成员说明</a>
 */
public class UserApi extends QyApi {
	private final TokenHolder tokenHolder;

	public UserApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 创建成员
	 * 
	 * @param user
	 *            成员对象
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E5.88.9B.E5.BB.BA.E6.88.90.E5.91.98">创建成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult createUser(User user) throws WeixinException {
		String user_create_uri = getRequestUri("user_create_uri");
		return excute(user_create_uri, user);
	}

	/**
	 * 更新用户(如果非必须的字段未指定 则不更新该字段之前的设置值)
	 * 
	 * @param user
	 *            成员对象
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E6.9B.B4.E6.96.B0.E6.88.90.E5.91.98">更新成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult updateUser(User user) throws WeixinException {
		String user_update_uri = getRequestUri("user_update_uri");
		return excute(user_update_uri, user);
	}

	private JsonResult excute(String uri, User user) throws WeixinException {
		JSONObject obj = (JSONObject) JSON.toJSON(user);
		Object extattr = obj.remove("extattr");
		if (extattr != null) {
			JSONObject attrs = new JSONObject();
			attrs.put("attrs", extattr);
			obj.put("extattr", attrs);
		}
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(
				String.format(uri, token.getAccessToken()), obj.toJSONString());
		return response.getAsJsonResult();
	}

	/**
	 * 获取成员
	 * 
	 * @param userid
	 *            成员唯一ID
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E8.8E.B7.E5.8F.96.E6.88.90.E5.91.98">获取成员说明</a>
	 * @return 成员对象
	 * @throws WeixinException
	 */
	public User getUser(String userid) throws WeixinException {
		String user_get_uri = getRequestUri("user_get_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(String.format(user_get_uri,
				token.getAccessToken(), userid));
		JSONObject obj = response.getAsJson();
		Object attrs = obj.getJSONObject("extattr").remove("attrs");
		if (attrs != null) {
			obj.put("extattr", attrs);
		}
		return JSON.toJavaObject(obj, User.class);
	}

	/**
	 * code获取userid(管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同。)
	 * 
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @param agentid
	 *            跳转链接时所在的企业应用ID
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see com.foxinmy.weixin4j.qy.api.UserApi
	 * @return 成员对象
	 * @see {@link com.foxinmy.weixin4j.qy.api.UserApi#getUser(String)}
	 * @see {@link com.foxinmy.weixin4j.qy.api.UserApi#getUserIdByCode(String,int)}
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E8%8E%B7%E5%8F%96code">企业获取code</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%A0%B9%E6%8D%AEcode%E8%8E%B7%E5%8F%96%E6%88%90%E5%91%98%E4%BF%A1%E6%81%AF">根据code获取成员信息</a>
	 * @throws WeixinException
	 */
	public User getUserByCode(String code, int agentid) throws WeixinException {
		return getUser(getUserIdByCode(code, agentid).getString("UserId"));
	}

	/**
	 * 根据code获取成员信息
	 * 
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @param agentid
	 *            跳转链接时所在的企业应用ID
	 * @return { "UserId":"USERID", "DeviceId":"DEVICEID" }
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E8%8E%B7%E5%8F%96code">企业获取code</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%A0%B9%E6%8D%AEcode%E8%8E%B7%E5%8F%96%E6%88%90%E5%91%98%E4%BF%A1%E6%81%AF">根据code获取成员信息</a>
	 * @throws WeixinException
	 */
	public JSONObject getUserIdByCode(String code, int agentid)
			throws WeixinException {
		String user_getid_uri = getRequestUri("user_getid_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(String.format(user_getid_uri,
				token.getAccessToken(), code, agentid));
		return response.getAsJson();
	}

	/**
	 * 获取部门成员
	 * 
	 * @param departId
	 *            部门ID 必须
	 * @param fetchChild
	 *            是否递归获取子部门下面的成员 非必须
	 * @param userStatus
	 *            成员状态 status可叠加 非必须
	 * @param findDetail
	 *            是否获取详细信息
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E6.88.90.E5.91.98">获取部门成员说明</a>
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> listUser(int departId, boolean fetchChild,
			UserStatus userStatus, boolean findDetail) throws WeixinException {
		String user_list_uri = findDetail ? getRequestUri("user_list_uri")
				: getRequestUri("user_slist_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(String.format(user_list_uri,
				token.getAccessToken(), departId, fetchChild ? 1 : 0,
				userStatus.getVal()));
		List<User> list = null;
		if (findDetail) {
			JSONArray arrays = response.getAsJson().getJSONArray("userlist");
			list = new ArrayList<User>(arrays.size());
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject obj = arrays.getJSONObject(i);
				JSONObject ex = obj.getJSONObject("extattr");
				Object attrs = null;
				if (ex != null && (attrs = ex.remove("attrs")) != null) {
					obj.put("extattr", attrs);
				}
				list.add(JSON.toJavaObject(obj, User.class));
			}
		} else {
			list = JSON.parseArray(response.getAsJson().getString("userlist"),
					User.class);
		}
		return list;
	}

	/**
	 * 获取部门下所有状态成员(不进行递归)
	 * 
	 * @param departId
	 *            部门ID
	 * @see {@link com.foxinmy.weixin4j.qy.api.UserApi#listUser(int, boolean,UserStatus)}
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> listUser(int departId) throws WeixinException {
		return listUser(departId, false, UserStatus.BOTH, false);
	}

	/**
	 * 删除成员
	 * 
	 * @param userid
	 *            成员ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E5.88.A0.E9.99.A4.E6.88.90.E5.91.98">删除成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult deleteUser(String userid) throws WeixinException {
		String user_delete_uri = getRequestUri("user_delete_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(String.format(user_delete_uri,
				token.getAccessToken(), userid));
		return response.getAsJsonResult();
	}

	/**
	 * 批量删除成员
	 * 
	 * @param userIds
	 *            成员列表
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E6.89.B9.E9.87.8F.E5.88.A0.E9.99.A4.E6.88.90.E5.91.98"
	 *      >批量删除成员说明</a
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult batchDeleteUser(List<String> userIds)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("useridlist", userIds);
		String user_delete_uri = getRequestUri("user_batchdelete_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(String.format(user_delete_uri,
				token.getAccessToken(), obj.toJSONString()));
		return response.getAsJsonResult();
	}

	/**
	 * 开启二次验证成功时调用(管理员须拥有userid对应员工的管理权限)
	 * 
	 * @param userid
	 *            成员ID
	 * @return 调用结果
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%B3%E6%B3%A8%E4%B8%8E%E5%8F%96%E6%B6%88%E5%85%B3%E6%B3%A8">二次验证说明</a>
	 * @throws WeixinException
	 */
	public JsonResult authsucc(String userId) throws WeixinException {
		String user_authsucc_uri = getRequestUri("user_authsucc_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(String.format(user_authsucc_uri,
				token.getAccessToken(), userId));
		return response.getAsJsonResult();
	}

	/**
	 * 邀请成员关注(管理员须拥有该成员的查看权限)
	 * 
	 * @param userId
	 *            成员ID
	 * @param tips
	 *            推送到微信上的提示语（只有认证号可以使用）。当使用微信推送时，该字段默认为“请关注XXX企业号”，邮件邀请时，该字段无效。
	 * @return 邀请类型
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E9.82.80.E8.AF.B7.E6.88.90.E5.91.98.E5.85.B3.E6.B3.A8">邀请成员关注说明</a>
	 * @throws WeixinException
	 */
	public InviteType inviteUser(String userId, String tips)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("userid", userId);
		if (StringUtil.isBlank(tips)) {
			obj.put("invite_tips", tips);
		}
		String invite_user_uri = getRequestUri("invite_user_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(
				String.format(invite_user_uri, token.getAccessToken()),
				obj.toJSONString());
		int type = response.getAsJson().getIntValue("type");
		if (type == 1) {
			return InviteType.WEIXIN;
		} else if (type == 2) {
			return InviteType.EMAIL;
		} else {
			return null;
		}
	}
}
