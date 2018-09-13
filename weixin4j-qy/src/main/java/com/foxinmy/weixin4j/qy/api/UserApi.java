package com.foxinmy.weixin4j.qy.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.OUserInfo;
import com.foxinmy.weixin4j.qy.model.Party;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.qy.type.InviteType;
import com.foxinmy.weixin4j.qy.type.UserStatus;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.NameValue;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 成员API
 * 
 * @className UserApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月19日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.qy.model.User
 * @see <a href= "http://work.weixin.qq.com/api/doc#10018">管理成员说明</a>
 */
public class UserApi extends QyApi {
	private final MediaApi mediaApi;
	private final PartyApi partyApi;
	private final TokenManager tokenManager;

	public UserApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
		this.mediaApi = new MediaApi(tokenManager);
		this.partyApi = new PartyApi(tokenManager);
	}

	/**
	 * 创建成员
	 * 
	 * @param user
	 *            成员对象
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a href= "http://work.weixin.qq.com/api/doc#10018"> 创建成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult createUser(User user) throws WeixinException {
		String user_create_uri = getRequestUri("user_create_uri");
		return excute(user_create_uri, user, null);
	}

	/**
	 * 创建成员
	 * 
	 * @param user
	 *            成员对象
	 * @param avatar
	 *            头像文件 可为空
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a href= "http://work.weixin.qq.com/api/doc#10018"> 创建成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult createUser(User user, InputStream avatar)
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
	 * @see <a href= "http://work.weixin.qq.com/api/doc#10020"> 更新成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult updateUser(User user) throws WeixinException {
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
	 * @see <a href= "http://work.weixin.qq.com/api/doc#10020"> 更新成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult updateUser(User user, InputStream avatar)
			throws WeixinException {
		String user_update_uri = getRequestUri("user_update_uri");
		return excute(user_update_uri, user, avatar);
	}

	private ApiResult excute(String uri, User user, InputStream avatar)
			throws WeixinException {
		JSONObject obj = (JSONObject) JSON.toJSON(user);
		Object val = obj.remove("extattr");
		if (val != null) {
			JSONObject attrs = new JSONObject();
			attrs.put("attrs", val);
			obj.put("extattr", attrs);
		}
		val = obj.remove("status");
		if (val != null) {
			obj.put("enable", val);
		}
		if (avatar != null) {
			obj.put("avatar_mediaid", mediaApi.uploadMedia(0, avatar, null));
		} else {
			obj.put("avatar_mediaid", obj.remove("avatar"));
		}
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(uri, token.getAccessToken()), obj.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 获取成员
	 * 
	 * @param userid
	 *            成员唯一ID
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a href= "http://work.weixin.qq.com/api/doc#10019">获取成员说明</a>
	 * @return 成员对象
	 * @throws WeixinException
	 */
	public User getUser(String userid) throws WeixinException {
		String user_get_uri = getRequestUri("user_get_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				user_get_uri, token.getAccessToken(), userid));
		JSONObject obj = response.getAsJson();
		Object attrs = obj.remove("extattr");
		User user = JSON.toJavaObject(obj, User.class);
		if (attrs != null) {
			user.setExtattr(JSON.parseArray(
					((JSONObject) attrs).getString("attrs"), NameValue.class));
		}
		return user;
	}

	/**
	 * 根据code获取用户信息
	 * 
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @return 成员对象
	 * @see {@link #getUser(String)}
	 * @see {@link #getUserIdByCode(String)}
	 * @see <a href= "http://work.weixin.qq.com/api/doc#10028/根据code获取成员信息">
	 *      oauth授权获取用户信息</a>
	 * @throws WeixinException
	 */
	public User getUserByCode(String code) throws WeixinException {
		JSONObject result = getUserIdByCode(code);
		if (result.containsKey("user_ticket")) {
			String user_ticket_detail_uri = getRequestUri("user_ticket_detail_uri");
			Token token = tokenManager.getCache();
			WeixinResponse response = weixinExecutor.post(
					String.format(user_ticket_detail_uri,
							token.getAccessToken()),
					String.format("{\"user_ticket\":\"%s\"}",
							result.getString("user_ticket")));
			JSONObject obj = response.getAsJson();
			Object attrs = obj.remove("extattr");
			User user = JSON.toJavaObject(obj, User.class);
			if (attrs != null) {
				user.setExtattr(JSON.parseArray(
						((JSONObject) attrs).getString("attrs"),
						NameValue.class));
			}
			return user;
		} else {
			String userId = result.getString("UserId");
			if (StringUtil.isBlank(userId)) {
				userId = openid2userid(result.getString("OpenId"));
			}
			return getUser(userId);
		}
	}

	/**
	 * 根据code获取成员ID信息
	 * 
	 * @param code
	 *            通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	 * @return 换取结果
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10028">
	 *      oauth授权获取用户信息</a>
	 * @throws WeixinException
	 */
	public JSONObject getUserIdByCode(String code) throws WeixinException {
		String user_getid_uri = getRequestUri("user_getid_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				user_getid_uri, token.getAccessToken(), code));
		return response.getAsJson();
	}

	/**
	 * 获取企业号管理员登录信息
	 * 
	 * @param authCode
	 *            oauth2.0授权企业号管理员登录产生的code
	 * @return 登陆信息
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E4%BC%81%E4%B8%9A%E7%AE%A1%E7%90%86%E5%91%98%E7%99%BB%E5%BD%95%E4%BF%A1%E6%81%AF">
	 *      授权获取企业号管理员登录信息</a>
	 * @see com.foxinmy.weixin4j.qy.model.OUserInfo
	 * @throws WeixinException
	 */
	public OUserInfo getOUserInfoByCode(String authCode) throws WeixinException {
		Token token = tokenManager.getCache();
		String oauth_logininfo_uri = getRequestUri("oauth_logininfo_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(oauth_logininfo_uri, token.getAccessToken()),
				String.format("{\"auth_code\":\"%s\"}", authCode));
		return JSON.parseObject(response.getAsString(), OUserInfo.class);
	}

	/**
	 * 获取部门成员
	 * 
	 * @param partyId
	 *            部门ID
	 * @param fetchChild
	 *            是否递归获取子部门下面的成员
	 * @param userStatus
	 *            成员状态 status可叠加 未填写则默认为未关注(4)
	 * @param findDetail
	 *            是否获取详细信息
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10061"> 获取部门成员说明</a>
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> listUser(int partyId, boolean fetchChild,
			UserStatus userStatus, boolean findDetail) throws WeixinException {
		String user_list_uri = findDetail ? getRequestUri("user_list_uri")
				: getRequestUri("user_slist_uri");
		Token token = tokenManager.getCache();
		if (userStatus == null) {
			userStatus = UserStatus.UNFOLLOW;
		}
		WeixinResponse response = weixinExecutor.get(String.format(
				user_list_uri, token.getAccessToken(), partyId, fetchChild ? 1
						: 0, userStatus.getVal()));
		List<User> list = null;
		if (findDetail) {
			JSONArray arrays = response.getAsJson().getJSONArray("userlist");
			list = new ArrayList<User>(arrays.size());
			for (int i = 0; i < arrays.size(); i++) {
				JSONObject obj = arrays.getJSONObject(i);
				Object attrs = obj.remove("extattr");
				User user = JSON.toJavaObject(obj, User.class);
				if (attrs != null) {
					user.setExtattr(JSON.parseArray(
							((JSONObject) attrs).getString("attrs"),
							NameValue.class));
				}
				list.add(user);
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
	 * @param partyId
	 *            部门ID
	 * @see {@link #listUser(int, boolean, UserStatus,boolean)}
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> listUser(int partyId) throws WeixinException {
		return listUser(partyId, false, UserStatus.BOTH, false);
	}

	/**
	 * 获取权限范围内的所有成员列表
	 * 
	 * @param userStatus
	 *            成员状态 未填写则默认为全部状态下的成员
	 * @return 成员列表
	 * @see {@link #listUser(int, boolean, UserStatus,boolean)}
	 * @see {@link PartyApi#listParty(int)}
	 * @throws WeixinException
	 */
	public List<User> listAllUser(UserStatus userStatus) throws WeixinException {
		List<User> users = null;
		List<Party> parties = partyApi.listParty(0);
		if (!parties.isEmpty()) {
			if (userStatus == null) {
				userStatus = UserStatus.BOTH;
			}
			users = new ArrayList<User>();
			for (Party party : parties) {
				users.addAll(listUser(party.getId(), true, userStatus, true));
			}
		}
		return users;
	}

	/**
	 * 删除成员
	 * 
	 * @param userid
	 *            成员ID
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10030"> 删除成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult deleteUser(String userid) throws WeixinException {
		String user_delete_uri = getRequestUri("user_delete_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				user_delete_uri, token.getAccessToken(), userid));
		return response.getAsResult();
	}

	/**
	 * 批量删除成员
	 * 
	 * @param userIds
	 *            成员列表
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10060" >批量删除成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult batchDeleteUser(List<String> userIds)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("useridlist", userIds);
		String user_delete_uri = getRequestUri("user_batchdelete_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(user_delete_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 开启二次验证成功时调用(管理员须拥有userid对应员工的管理权限)
	 * 
	 * @param userid
	 *            成员ID
	 * @return 调用结果
	 * @see <a href= "https://work.weixin.qq.com/api/doc#11378"> 二次验证说明</a>
	 * @throws WeixinException
	 */
	public ApiResult authsucc(String userId) throws WeixinException {
		String user_authsucc_uri = getRequestUri("user_authsucc_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				user_authsucc_uri, token.getAccessToken(), userId));
		return response.getAsResult();
	}

	/**
	 * 邀请成员关注(管理员须拥有该成员的查看权限)
	 * 
	 * @param userId
	 *            成员ID
	 * @param tips
	 *            推送到微信上的提示语（只有认证号可以使用）。当使用微信推送时，该字段默认为“请关注XXX企业号”，邮件邀请时，该字段无效。
	 * @return 邀请类型
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%88%90%E5%91%98#.E9.82.80.E8.AF.B7.E6.88.90.E5.91.98.E5.85.B3.E6.B3.A8">
	 *      邀请成员关注说明</a>
	 * @throws WeixinException
	 */
	public InviteType inviteUser(String userId, String tips)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("userid", userId);
		obj.put("invite_tips", tips);
		String invite_user_uri = getRequestUri("invite_user_uri");
		Token token = tokenManager.getCache();
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
	 * @see <a href= "https://work.weixin.qq.com/api/doc#11279">
	 *      userid与openid互换</a>
	 */
	public String[] userid2openid(String userid, int agentid)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("userid", userid);
		if (agentid > 0) {
			obj.put("agentid", agentid);
		}
		String userid2openid_uri = getRequestUri("userid2openid_uri");
		WeixinResponse response = weixinExecutor
				.post(String.format(userid2openid_uri,
						tokenManager.getAccessToken()), obj.toJSONString());
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
	 * @see <a href= "https://work.weixin.qq.com/api/doc#11279">
	 *      userid与openid互换</a>
	 */
	public String openid2userid(String openid) throws WeixinException {
		String openid2userid_uri = getRequestUri("openid2userid_uri");
		WeixinResponse response = weixinExecutor
				.post(String.format(openid2userid_uri,
						tokenManager.getAccessToken()),
						String.format("{\"openid\": \"%s\"}", openid));
		return response.getAsJson().getString("userid");
	}
}
