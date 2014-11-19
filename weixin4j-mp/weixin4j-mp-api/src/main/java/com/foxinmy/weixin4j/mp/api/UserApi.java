package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.model.Following;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.mp.model.User;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 用户相关API
 * 
 * @className UserApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.mp.model.User
 */
public class UserApi extends BaseApi {

	private final TokenHolder tokenHolder;

	public UserApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 获取token
	 * 
	 * @param code
	 *            用户授权后返回的code
	 * @return token对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF#.E7.AC.AC.E4.BA.8C.E6.AD.A5.EF.BC.9A.E9.80.9A.E8.BF.87code.E6.8D.A2.E5.8F.96.E7.BD.91.E9.A1.B5.E6.8E.88.E6.9D.83access_token">获取用户token</a>
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 */
	public OauthToken getOauthToken(String code) throws WeixinException {
		WeixinAccount weixinAccount = tokenHolder.getAccount();
		String user_token_uri = getRequestUri("sns_user_token_uri");
		Response response = request.get(String.format(user_token_uri,
				weixinAccount.getId(), weixinAccount.getSecret(), code));

		return response.getAsObject(new TypeReference<OauthToken>() {
		});
	}

	/**
	 * 获取用户信息
	 * 
	 * @param token
	 *            授权票据
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E7%BD%91%E9%A1%B5%E6%8E%88%E6%9D%83%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF#.E7.AC.AC.E5.9B.9B.E6.AD.A5.EF.BC.9A.E6.8B.89.E5.8F.96.E7.94.A8.E6.88.B7.E4.BF.A1.E6.81.AF.28.E9.9C.80scope.E4.B8.BA_snsapi_userinfo.29">拉取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 * @see com.foxinmy.weixin4j.mp.model.OauthToken
	 *      {@link com.foxinmy.weixin4j.mp.api.UserApi#getOauthToken(String)}
	 */
	public User getUser(OauthToken token) throws WeixinException {
		String user_info_uri = getRequestUri("sns_user_info_uri");
		Response response = request.get(String.format(user_info_uri,
				token.getAccessToken(), token.getOpenid()));

		return response.getAsObject(new TypeReference<User>() {
		});
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
	 * @return 用户对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%9F%BA%E6%9C%AC%E4%BF%A1%E6%81%AF">获取用户信息</a>
	 * @see com.foxinmy.weixin4j.mp.model.User
	 */
	public User getUser(String openId) throws WeixinException {
		String user_info_uri = getRequestUri("api_user_info_uri");
		Token token = tokenHolder.getToken();
		Response response = request.get(String.format(user_info_uri,
				token.getAccessToken(), openId));

		return response.getAsObject(new TypeReference<User>() {
		});
	}

	/**
	 * 获取用户一定数量(10000)的关注者列表
	 * 
	 * @param nextOpenId
	 *            下一次拉取数据的openid
	 * @return 关注信息
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%85%B3%E6%B3%A8%E8%80%85%E5%88%97%E8%A1%A8">获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 */
	public Following getFollowing(String nextOpenId) throws WeixinException {
		String fllowing_uri = getRequestUri("following_uri");
		Token token = tokenHolder.getToken();
		Response response = request.get(String.format(fllowing_uri,
				token.getAccessToken(), nextOpenId == null ? "" : nextOpenId));

		Following following = response
				.getAsObject(new TypeReference<Following>() {
				});

		if (following.getCount() > 0) {
			List<String> openIds = JSON.parseArray(following.getDataJson()
					.getString("openid"), String.class);
			List<User> userList = new ArrayList<User>();
			for (String openId : openIds) {
				userList.add(getUser(openId));
			}
			following.setUserList(userList);
		}
		return following;
	}

	/**
	 * 获取用户全部的关注者列表
	 * <p>
	 * 当公众号关注者数量超过10000时,可通过填写next_openid的值,从而多次拉取列表的方式来满足需求,
	 * 将上一次调用得到的返回中的next_openid值,作为下一次调用中的next_openid值
	 * </p>
	 * 
	 * @return 用户对象集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%85%B3%E6%B3%A8%E8%80%85%E5%88%97%E8%A1%A8">获取关注者列表</a>
	 * @see com.foxinmy.weixin4j.mp.model.Following
	 * @see com.foxinmy.weixin4j.mp.api.UserApi#getFollowing(String)
	 */
	public List<User> getAllFollowing() throws WeixinException {
		List<User> userList = new ArrayList<User>();
		String nextOpenId = null;
		Following f = null;
		for (;;) {
			f = getFollowing(nextOpenId);
			if (f.getCount() == 0) {
				break;
			}
			userList.addAll(f.getUserList());
			nextOpenId = f.getNextOpenId();
		}
		return userList;
	}

	/**
	 * 设置用户备注名
	 * 
	 * @param openId
	 *            用户ID
	 * @param remark
	 *            备注名
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%E5%A4%87%E6%B3%A8%E5%90%8D%E6%8E%A5%E5%8F%A3">设置用户备注名</a>
	 */
	public JsonResult remarkUserName(String openId, String remark)
			throws WeixinException {
		String updateremark_uri = getRequestUri("updateremark_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("openid", openId);
		obj.put("remark", remark);
		Response response = request.post(
				String.format(updateremark_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJsonResult();
	}
}
