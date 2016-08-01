package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.Following;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.mp.type.Lang;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 用户相关API
 * 
 * @className UserApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.mp.model.User
 */
public class UserApi extends MpApi {

	private final TokenManager tokenManager;

	public UserApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @return 用户对象
	 * @throws WeixinException
	 * @see {@link #getUser(String,Lang)}
	 */
	public User getUser(String openId) throws WeixinException {
		return getUser(openId, Lang.zh_CN);
	}

	/**
	 * 获取用户信息
	 * <p>
	 * 在关注者与公众号产生消息交互后,公众号可获得关注者的OpenID（加密后的微信号,每个用户对每个公众号的OpenID是唯一的,对于不同公众号,
	 * 同一用户的openid不同）,公众号可通过本接口来根据OpenID获取用户基本信息,包括昵称、头像、性别、所在城市、语言和关注时间
	 * </p>
	 * 
	 * @param openId
	 *            用户对应的ID
	 * @param lang
	 *            国家地区语言版本
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.type.Lang
	 * @see com.foxinmy.weixin4j.mp.model.User
	 */
	public User getUser(String openId, Lang lang) throws WeixinException {
		String user_info_uri = getRequestUri("api_user_info_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor
				.get(String.format(user_info_uri, token.getAccessToken(), openId, lang.name()));

		return response.getAsObject(new TypeReference<User>() {
		});
	}

	/**
	 * 批量获取用户信息
	 * 
	 * @param openIds
	 *            用户ID
	 * @return 用户列表
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @throws WeixinException
	 * @see {@link #getUsers(Lang,String[])}
	 */
	public List<User> getUsers(String... openIds) throws WeixinException {
		return getUsers(Lang.zh_CN, openIds);
	}

	/**
	 * 批量获取用户信息
	 * 
	 * @param lang
	 *            国家地区语言版本
	 * @param openIds
	 *            用户ID 最多100个
	 * @return 用户列表
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.type.Lang
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @throws WeixinException
	 */
	public List<User> getUsers(Lang lang, String... openIds) throws WeixinException {
		String api_users_info_uri = getRequestUri("api_users_info_uri");
		StringBuilder parameter = new StringBuilder();
		parameter.append("{\"user_list\": [");
		for (String openId : openIds) {
			parameter.append("{\"openid\": \"").append(openId).append("\"");
			parameter.append(",\"lang\": \"").append(lang.name()).append("\"").append("},");
		}
		parameter.delete(parameter.length() - 1, parameter.length());
		parameter.append("]}");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(String.format(api_users_info_uri, token.getAccessToken()),
				parameter.toString());

		return JSON.parseArray(response.getAsJson().getString("user_info_list"), User.class);
	}

	/**
	 * 获取公众号一定数量(10000)的关注者列表 <font corlor="red">请慎重使用</font>
	 * 
	 * @param nextOpenId
	 *            下一次拉取数据的openid 不填写则默认从头开始拉取
	 * @return 关注者信息 <font color="red">包含用户的详细信息</font>
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      获取关注者列表</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      批量获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @see com.foxinmy.weixin4j.mp.model.User
	 */
	public Following getFollowing(String nextOpenId) throws WeixinException {
		Following following = getFollowingOpenIds(nextOpenId);
		if (following.getCount() > 0) {
			List<User> users = new ArrayList<User>(following.getCount());
			for (int i = 1; i <= (int) Math.ceil(following.getCount() / 100d); i++) {
				users.addAll(getUsers(following.getOpenIds()
						.subList((i - 1) * 100, Math.min(i * 100, following.getCount())).toArray(new String[] {})));
			}
			following.setUserList(users);
		}
		return following;
	}

	/**
	 * 获取公众号一定数量(10000)的关注者列表
	 * 
	 * @param nextOpenId
	 *            下一次拉取数据的openid 不填写则默认从头开始拉取
	 * @return 关注者信息 <font color="red">不包含用户的详细信息</font>
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 */
	public Following getFollowingOpenIds(String nextOpenId) throws WeixinException {
		String following_uri = getRequestUri("following_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor
				.get(String.format(following_uri, token.getAccessToken(), nextOpenId == null ? "" : nextOpenId));

		JSONObject result = response.getAsJson();
		Following following = JSON.toJavaObject(result, Following.class);

		if (following.getCount() > 0) {
			following.setOpenIds(JSON.parseArray(result.getJSONObject("data").getString("openid"), String.class));
		}
		return following;
	}

	/**
	 * 获取公众号全部的关注者列表 <font corlor="red">请慎重使用</font>
	 * <p>
	 * 当公众号关注者数量超过10000时,可通过填写next_openid的值,从而多次拉取列表的方式来满足需求,
	 * 将上一次调用得到的返回中的next_openid值,作为下一次调用中的next_openid值
	 * </p>
	 * 
	 * @return 用户对象集合
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      获取关注者列表</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN">
	 *      批量获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @see #getFollowing(String)
	 */
	public List<User> getAllFollowing() throws WeixinException {
		List<User> userList = new ArrayList<User>();
		String nextOpenId = null;
		Following f = null;
		for (;;) {
			f = getFollowing(nextOpenId);
			if (f.hasContent()) {
				userList.addAll(f.getUserList());
				nextOpenId = f.getNextOpenId();
				continue;
			}
			break;
		}
		return userList;
	}

	/**
	 * 获取公众号全部的关注者列表 <font corlor="red">请慎重使用</font>
	 * <p>
	 * 当公众号关注者数量超过10000时,可通过填写next_openid的值,从而多次拉取列表的方式来满足需求,
	 * 将上一次调用得到的返回中的next_openid值,作为下一次调用中的next_openid值
	 * </p>
	 * 
	 * @return 用户openid集合
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN">
	 *      获取关注者列表</a>
	 * @see #getFollowingOpenIds(String)
	 */
	public List<String> getAllFollowingOpenIds() throws WeixinException {
		List<String> openIds = new ArrayList<String>();
		String nextOpenId = null;
		Following f = null;
		for (;;) {
			f = getFollowingOpenIds(nextOpenId);
			if (f.hasContent()) {
				openIds.addAll(f.getOpenIds());
				nextOpenId = f.getNextOpenId();
				continue;
			}
			break;
		}
		return openIds;
	}

	/**
	 * 设置用户备注名
	 * 
	 * @param openId
	 *            用户ID
	 * @param remark
	 *            备注名
	 * @throws WeixinException
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140838&token=&lang=zh_CN">
	 *      设置用户备注名</a>
	 */
	public ApiResult remarkUserName(String openId, String remark) throws WeixinException {
		String username_remark_uri = getRequestUri("username_remark_uri");
		Token token = tokenManager.getCache();
		JSONObject obj = new JSONObject();
		obj.put("openid", openId);
		obj.put("remark", remark);
		WeixinResponse response = weixinExecutor.post(String.format(username_remark_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsResult();
	}
}
