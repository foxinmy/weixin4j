package com.foxinmy.weixin4j.qy.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.type.UserStatus;
import com.foxinmy.weixin4j.token.TokenHolder;

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
		Response response = request.post(
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
		Response response = request.post(String.format(user_get_uri,
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
	 * @return {"UserId":"员工UserID","DeviceId":"手机设备号(由微信在安装时随机生成)"}
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E8%8E%B7%E5%8F%96code">企业获取code</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%A0%B9%E6%8D%AEcode%E8%8E%B7%E5%8F%96%E6%88%90%E5%91%98%E4%BF%A1%E6%81%AF">根据code获取成员信息</a>
	 * @throws WeixinException
	 */
	public JSONObject getUserid(String code, int agentid)
			throws WeixinException {
		String user_getid_uri = getRequestUri("user_getid_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(String.format(user_getid_uri,
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
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E6.88.90.E5.91.98">获取部门成员说明</a>
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> listUser(int departId, boolean fetchChild,
			UserStatus userStatus) throws WeixinException {
		String user_list_uri = getRequestUri("user_list_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(String.format(user_list_uri,
				token.getAccessToken(), departId, fetchChild ? 1 : 0,
				userStatus.getVal()));
		return JSON.parseArray(response.getAsJson().getString("userlist"),
				User.class);
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
		return listUser(departId, false, UserStatus.BOTH);
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
		Response response = request.post(String.format(user_delete_uri,
				token.getAccessToken(), userid));
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
	public JsonResult authsucc(String userid) throws WeixinException {
		String user_authsucc_uri = getRequestUri("user_authsucc_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(String.format(user_authsucc_uri,
				token.getAccessToken(), userid));
		return response.getAsJsonResult();
	}
}
