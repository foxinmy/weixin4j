package com.foxinmy.weixin4j.qy.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.Tag;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 标签API
 * 
 * @className TagApi
 * @author jy
 * @date 2014年11月19日
 * @since JDK 1.7
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE">管理标签</a>
 */
public class TagApi extends QyApi {
	private final TokenHolder tokenHolder;

	public TagApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 创建标签(创建的标签属于管理组；默认为加锁状态。加锁状态下只有本管理组才可以增删成员，解锁状态下其它管理组也可以增删成员)
	 * 
	 * @param tag
	 *            标签对象；</br>标签名称，长度为1~64个字节，标签名不可与其他标签重名；</br>标签id，整型，
	 *            指定此参数时新增的标签会生成对应的标签id，不指定时则以目前最大的id自增。
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E5.88.9B.E5.BB.BA.E6.A0.87.E7.AD.BE">创建标签说明</a>
	 * @return 标签ID
	 * @throws WeixinException
	 */
	public int createTag(Tag tag) throws WeixinException {
		String tag_create_uri = getRequestUri("tag_create_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = (JSONObject) JSON.toJSON(tag);
		if (obj.getIntValue("tagid") <= 0) {
			obj.remove("tagid");
		}
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_create_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJson().getIntValue("tagid");
	}

	/**
	 * 更新标签(管理组必须是指定标签的创建者)
	 * 
	 * @param tag
	 *            标签信息
	 * @see <a href=
	 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E6.9B.B4.E6.96.B0.E6.A0.87.E7.AD.BE.E5.90.8D.E5.AD.97"
	 *      >更新标签说明</a>
	 * @return 处理结果
	 * @see com.foxinmy.weixin4j.qy.model.Tag
	 * @throws WeixinException
	 */
	public JsonResult updateTag(Tag tag) throws WeixinException {
		String tag_update_uri = getRequestUri("tag_update_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_update_uri, token.getAccessToken()),
				JSON.toJSONString(tag));
		return response.getAsJsonResult();
	}

	/**
	 * 删除标签(管理组必须是指定标签的创建者，并且标签的成员列表为空。)
	 * 
	 * @param tagId
	 *            标签ID
	 * @return 处理结果
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E5.88.A0.E9.99.A4.E6.A0.87.E7.AD.BE">删除标签说明</a>
	 * @throws WeixinException
	 */
	public JsonResult deleteTag(int tagId) throws WeixinException {
		String tag_delete_uri = getRequestUri("tag_delete_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				tag_delete_uri, token.getAccessToken(), tagId));
		return response.getAsJsonResult();
	}

	/**
	 * 获取标签列表
	 * 
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E8.8E.B7.E5.8F.96.E6.A0.87.E7.AD.BE.E5.88.97.E8.A1.A8">获取标签列表说明</a>
	 * @return 标签列表
	 * @see com.foxinmy.weixin4j.qy.model.Tag
	 * @throws WeixinException
	 */
	public List<Tag> listTag() throws WeixinException {
		String tag_list_uri = getRequestUri("tag_list_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(tag_list_uri,
				token.getAccessToken()));
		return JSON.parseArray(response.getAsJson().getString("taglist"),
				Tag.class);
	}

	/**
	 * 获取标签成员(管理组须拥有“获取标签成员”的接口权限，返回列表仅包含管理组管辖范围的成员。)
	 * 
	 * @param tagId
	 *            标签ID
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E8.8E.B7.E5.8F.96.E6.A0.87.E7.AD.BE.E6.88.90.E5.91.98">获取标签成员说明</a>
	 * @return 成员列表
	 * @throws WeixinException
	 */
	public List<User> getTagUsers(int tagId) throws WeixinException {
		String tag_get_user_uri = getRequestUri("tag_get_user_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				tag_get_user_uri, token.getAccessToken(), tagId));
		return JSON.parseArray(response.getAsJson().getString("userlist"),
				User.class);
	}

	/**
	 * 新增标签成员(标签对管理组可见且未加锁，成员属于管理组管辖范围。)<br>
	 * <font color="red">若部分userid非法，则在text中体现</font>
	 * 
	 * @param tagId
	 *            标签ID
	 * @param userIds
	 *            成员ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E5.A2.9E.E5.8A.A0.E6.A0.87.E7.AD.BE.E6.88.90.E5.91.98">新增标签成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult addTagUsers(int tagId, List<String> userIds)
			throws WeixinException {
		String tag_add_user_uri = getRequestUri("tag_add_user_uri");
		return excuteUsers(tag_add_user_uri, tagId, userIds);
	}

	/**
	 * 删除标签成员(标签对管理组未加锁，成员属于管理组管辖范围)<br>
	 * <font color="red">若部分userid非法，则在text中体现</font>
	 * 
	 * @param tagId
	 *            标签ID
	 * @param userIds
	 *            成员ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E6%A0%87%E7%AD%BE#.E5.88.A0.E9.99.A4.E6.A0.87.E7.AD.BE.E6.88.90.E5.91.98">删除标签成员说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult deleteTagUsers(int tagId, List<String> userIds)
			throws WeixinException {
		String tag_delete_user_uri = getRequestUri("tag_delete_user_uri");
		return excuteUsers(tag_delete_user_uri, tagId, userIds);
	}

	private JsonResult excuteUsers(String uri, int tagId, List<String> userIds)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("tagid", tagId);
		obj.put("userlist", userIds);
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(uri, token.getAccessToken()), obj.toJSONString());
		obj = response.getAsJson();
		JsonResult result = JSON.toJavaObject(obj, JsonResult.class);
		result.setText(obj.getString("invalidlist"));
		return result;
	}
}
