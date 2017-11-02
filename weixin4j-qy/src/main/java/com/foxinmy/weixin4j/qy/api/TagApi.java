package com.foxinmy.weixin4j.qy.api;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.Contacts;
import com.foxinmy.weixin4j.qy.model.IdParameter;
import com.foxinmy.weixin4j.qy.model.Tag;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 标签API
 *
 * @className TagApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月19日
 * @since JDK 1.6
 * @see <a href="http://work.weixin.qq.com/api/doc#10915">管理标签</a>
 */
public class TagApi extends QyApi {
	private final TokenManager tokenManager;

	public TagApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 创建标签(创建的标签属于管理组；默认为加锁状态。加锁状态下只有本管理组才可以增删成员，解锁状态下其它管理组也可以增删成员)
	 *
	 * @param tag
	 *            标签对象；</br> 标签名称，长度限制为32个字（汉字或英文字母），标签名不可与其他标签重名。</br> 标签id，整型，
	 *            指定此参数时新增的标签会生成对应的标签id，不指定时则以目前最大的id自增。
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10915"> 创建标签说明</a>
	 * @return 标签ID
	 * @throws WeixinException
	 */
	public int createTag(Tag tag) throws WeixinException {
		String tag_create_uri = getRequestUri("tag_create_uri");
		Token token = tokenManager.getCache();
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
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10919" >更新标签说明</a>
	 * @return 处理结果
	 * @see com.foxinmy.weixin4j.qy.model.Tag
	 * @throws WeixinException
	 */
	public ApiResult updateTag(Tag tag) throws WeixinException {
		String tag_update_uri = getRequestUri("tag_update_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(tag_update_uri, token.getAccessToken()),
				JSON.toJSONString(tag));
		return response.getAsResult();
	}

	/**
	 * 删除标签(管理组必须是指定标签的创建者，并且标签的成员列表为空。)
	 *
	 * @param tagId
	 *            标签ID
	 * @return 处理结果
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10920"> 删除标签说明</a>
	 * @throws WeixinException
	 */
	public ApiResult deleteTag(int tagId) throws WeixinException {
		String tag_delete_uri = getRequestUri("tag_delete_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				tag_delete_uri, token.getAccessToken(), tagId));
		return response.getAsResult();
	}

	/**
	 * 获取标签列表
	 *
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10926"> 获取标签列表说明</a>
	 * @return 标签列表
	 * @see com.foxinmy.weixin4j.qy.model.Tag
	 * @throws WeixinException
	 */
	public List<Tag> listTag() throws WeixinException {
		String tag_list_uri = getRequestUri("tag_list_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				tag_list_uri, token.getAccessToken()));
		return JSON.parseArray(response.getAsJson().getString("taglist"),
				Tag.class);
	}

	/**
	 * 获取标签成员(管理组须拥有“获取标签成员”的接口权限，返回列表仅包含管理组管辖范围的成员。)
	 *
	 * @param tagId
	 *            标签ID
	 * @see com.foxinmy.weixin4j.qy.model.Contacts
	 * @see com.foxinmy.weixin4j.qy.model.User
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10921"> 获取标签成员说明</a>
	 * @return 成员列表<font color="red">Contacts#getUsers</font>和部门列表<font
	 *         color="red">Contacts#getPartyIds</font>
	 * @throws WeixinException
	 */
	public Contacts getTagUsers(int tagId) throws WeixinException {
		String tag_get_user_uri = getRequestUri("tag_get_user_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				tag_get_user_uri, token.getAccessToken(), tagId));
		JSONObject obj = response.getAsJson();
		Contacts contacts = new Contacts();
		contacts.setUsers(JSON.parseArray(obj.getString("userlist"), User.class));
		contacts.setPartyIds(JSON.parseArray(obj.getString("partylist"),
				Integer.class));
		contacts.putTagIds(tagId);
		return contacts;
	}

	/**
	 * 新增标签成员(标签对管理组可见且未加锁，成员属于管理组管辖范围。)
	 *
	 * @param tagId
	 *            标签ID
	 * @param userIds
	 *            企业成员ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过1000
	 * @param partyIds
	 *            企业部门ID列表，注意：userlist、partylist不能同时为空，单次请求长度不超过100
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10923"> 新增标签成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.model.IdParameter
	 * @return 非法的userIds和partyIds
	 * @throws WeixinException
	 */
	public IdParameter addTagUsers(int tagId, List<String> userIds,
			List<Integer> partyIds) throws WeixinException {
		String tag_add_user_uri = getRequestUri("tag_add_user_uri");
		return excuteUsers(tag_add_user_uri, tagId, userIds, partyIds);
	}

	/**
	 * 删除标签成员(标签对管理组未加锁，成员属于管理组管辖范围)<br>
	 *
	 * @param tagId
	 *            标签ID
	 * @param userIds
	 *            企业成员ID列表，注意：userlist、partylist不能同时为空
	 * @param partyIds
	 *            企业部门ID列表，注意：userlist、partylist不能同时为空
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10925"> 删除标签成员说明</a>
	 * @see com.foxinmy.weixin4j.qy.model.IdParameter
	 * @return 非法的userIds和partyIds
	 * @throws WeixinException
	 */
	public IdParameter deleteTagUsers(int tagId, List<String> userIds,
			List<Integer> partyIds) throws WeixinException {
		String tag_delete_user_uri = getRequestUri("tag_delete_user_uri");
		return excuteUsers(tag_delete_user_uri, tagId, userIds, partyIds);
	}

	private IdParameter excuteUsers(String uri, int tagId,
			List<String> userIds, List<Integer> partyIds)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("tagid", tagId);
		obj.put("userlist", userIds);
		obj.put("partylist", partyIds);
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(uri, token.getAccessToken()), obj.toJSONString());
		obj = response.getAsJson();
		IdParameter idParameter = IdParameter.get();
		if (obj.containsKey("invalidlist")) {
			idParameter.setUserIds(Arrays.asList(obj.getString("invalidlist")
					.split(IdParameter.SEPARATORS)));
		}
		if (obj.containsKey("partylist")) {
			idParameter.setPartyIds(JSON.parseArray(obj.getString("partylist"),
					Integer.class));
		}
		return idParameter;
	}
}
