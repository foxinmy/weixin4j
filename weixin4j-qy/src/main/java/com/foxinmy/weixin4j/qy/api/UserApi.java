package com.foxinmy.weixin4j.qy.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.type.InviteType;
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
	private final MediaApi mediaApi;
	private final TokenHolder tokenHolder;

	public UserApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
		this.mediaApi = new MediaApi(tokenHolder);
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
		return excute(user_create_uri, user, null);
	}

	/**
	 * 创建成员
	 * 
	 * @param user
	 *            成员对象
	 * @param avatar
	 *            头像文件
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E5.88.9B.E5.BB.BA.E6.88.90.E5.91.98">创建成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult createUser(User user, InputStream avatar)
			throws WeixinException {
		String user_create_uri = getRequestUri("user_create_uri");
		return excute(user_create_uri, user, avatar);
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
		return excute(user_update_uri, user, null);
	}

	/**
	 * 更新用户(如果非必须的字段未指定 则不更新该字段之前的设置值)
	 * 
	 * @param user
	 *            成员对象
	 * @param avatar
	 *            头像文件
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E6.9B.B4.E6.96.B0.E6.88.90.E5.91.98">更新成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult updateUser(User user, InputStream avatar)
			throws WeixinException {
		String user_update_uri = getRequestUri("user_update_uri");
		return excute(user_update_uri, user, avatar);
	}

	private JsonResult excute(String uri, User user, InputStream avatar)
			throws WeixinException {
		JSONObject obj = (JSONObject) JSON.toJSON(user);
		Object extattr = obj.remove("extattr");
		if (extattr != null) {
			JSONObject attrs = new JSONObject();
			attrs.put("attrs", extattr);
			obj.put("extattr", attrs);
		}
		if (avatar != null) {
			obj.put("avatar_mediaid", mediaApi.uploadMedia(0, avatar, null));
		} else {
			obj.put("avatar_mediaid", obj.remove("avatar"));
		}
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
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
		WeixinResponse response = weixinExecutor.get(String.format(user_get_uri,
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
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @return 成员对象
	 * @see {@link #getUser(String)}
	 * @see {@link #getUserIdByCode(String,int)}
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E8%8E%B7%E5%8F%96code">企业获取code</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%A0%B9%E6%8D%AEcode%E8%8E%B7%E5%8F%96%E6%88%90%E5%91%98%E4%BF%A1%E6%81%AF">根据code获取成员信息</a>
	 * @throws WeixinException
	 */
	public User getUserByCode(String code) throws WeixinException {
		return getUser(getUserIdByCode(code).getString("UserId"));
	}

	/**
	 * 获取企业号管理员登录信息
	 * 
	 * @param providerToken
	 *            提供商的token
	 * @param authCode
	 *            oauth2.0授权企业号管理员登录产生的code
	 * @return 登陆信息
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%91%98%E7%99%BB%E5%BD%95%E4%BF%A1%E6%81%AF">授权获取企业号管理员登录信息</a>
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @throws WeixinException
	 */
	public OUserInfo getOUserInfoByCode(String providerToken, String authCode)
			throws WeixinException {
		String oauth_logininfo_uri = getRequestUri("oauth_logininfo_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(oauth_logininfo_uri, providerToken),
				String.format("{\"auth_code\":\"%s\"}", authCode));
		return JSON.parseObject(response.getAsString(), OUserInfo.class);
	}

	/**
	 * 根据code获取成员信息
	 * 
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @return { "UserId":"USERID", "DeviceId":"DEVICEID" }
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/indexo.php?title=%E4%BC%81%E4%B8%9A%E8%8E%B7%E5%8F%96code">企业获取code</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%A0%B9%E6%8D%AEcode%E8%8E%B7%E5%8F%96%E6%88%90%E5%91%98%E4%BF%A1%E6%81%AF">根据code获取成员信息</a>
	 * @throws WeixinException
	 */
	public JSONObject getUserIdByCode(String code) throws WeixinException {
		String user_getid_uri = getRequestUri("user_getid_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				user_getid_uri, token.getAccessToken(), code));
		return response.getAsJson();
	}

	/**
	 * 获取部门成员
	 * 
	 * @param partyId
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
		WeixinResponse response = weixinExecutor.get(String.format(
				user_list_uri, token.getAccessToken(), departId, fetchChild ? 1
						: 0, userStatus.getVal()));
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
	 * @see {@link #listUser(int, boolean,UserStatus)}
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
		WeixinResponse response = weixinExecutor.get(String.format(
				user_delete_uri, token.getAccessToken(), userid));
		return response.getAsJsonResult();
	}

	/**
	 * 批量删除成员
	 * 
	 * @param userIds
	 *            成员列表
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E6.89.B9.E9.87.8F.E5.88.A0.E9.99.A4.E6.88.90.E5.91.98"
	 *      >批量删除成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult batchDeleteUser(List<String> userIds)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("useridlist", userIds);
		String user_delete_uri = getRequestUri("user_batchdelete_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(String.format(
				user_delete_uri, token.getAccessToken(), obj.toJSONString()));
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
		WeixinResponse response = weixinExecutor.get(String.format(
				user_authsucc_uri, token.getAccessToken(), userId));
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
		obj.put("invite_tips", tips);
		String invite_user_uri = getRequestUri("invite_user_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
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

	/**
	 * userid转换成openid:该接口使用场景为微信支付、微信红包和企业转账，企业号用户在使用微信支付的功能时，
	 * 需要自行将企业号的userid转成openid。 在使用微信红包功能时，需要将应用id和userid转成appid和openid才能使用。
	 * 
	 * @param userid
	 *            企业号内的成员id 必填
	 * @param agentid
	 *            需要发送红包的应用ID，若只是使用微信支付和企业转账，则无需该参数 传入0或负数则忽略
	 * @return 结果数组 第一个元素为对应的openid 第二个元素则为应用的appid(如果有)
	 * @throws WeixinException
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=Userid%E4%B8%8Eopenid%E4%BA%92%E6%8D%A2%E6%8E%A5%E5%8F%A3">userid转换成openid</a>
	 */
	public String[] userid2openid(String userid, int agentid)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("userid", userid);
		if (agentid > 0) {
			obj.put("agentid", agentid);
		}
		String userid2openid_uri = getRequestUri("userid2openid_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(userid2openid_uri, tokenHolder.getAccessToken()),
				obj.toJSONString());
		obj = response.getAsJson();
		return new String[] { obj.getString("openid"), obj.getString("appid") };
	}

	/**
	 * openid转换成userid:该接口主要应用于使用微信支付、微信红包和企业转账之后的结果查询，
	 * 开发者需要知道某个结果事件的openid对应企业号内成员的信息时，可以通过调用该接口进行转换查询。
	 * 
	 * @param openid
	 *            在使用微信支付、微信红包和企业转账之后，返回结果的openid
	 * @return 该openid在企业号中对应的成员userid
	 * @throws WeixinException
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=Userid%E4%B8%8Eopenid%E4%BA%92%E6%8D%A2%E6%8E%A5%E5%8F%A3">openid转换成userid</a>
	 */
	public String openid2userid(String openid) throws WeixinException {
		String openid2userid_uri = getRequestUri("openid2userid_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(openid2userid_uri, tokenHolder.getAccessToken()),
				String.format("{\"openid\": \"%s\"}", openid));
		return response.getAsJson().getString("userid");
	}
}
