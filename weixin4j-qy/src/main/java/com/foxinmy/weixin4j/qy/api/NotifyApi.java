package com.foxinmy.weixin4j.qy.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.message.CustomeMessage;
import com.foxinmy.weixin4j.qy.message.NotifyMessage;
import com.foxinmy.weixin4j.qy.model.IdParameter;
import com.foxinmy.weixin4j.qy.type.KfType;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.tuple.NotifyTuple;

/**
 * 客服消息API
 * 
 * @className NotifyApi
 * @author jy.hu
 * @date 2014年11月21日
 * @since JDK 1.6
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E">发送接口说明</a>
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B%E5%8F%8A%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F">发送格式说明</a>
 * @see com.foxinmy.weixin4j.tuple.Text
 * @see com.foxinmy.weixin4j.tuple.Image
 * @see com.foxinmy.weixin4j.tuple.Voice
 * @see com.foxinmy.weixin4j.tuple.Video
 * @see com.foxinmy.weixin4j.tuple.File
 * @see com.foxinmy.weixin4j.tuple.News
 * @see com.foxinmy.weixin4j.tuple.MpNews
 */
public class NotifyApi extends QyApi {

	private final TokenHolder tokenHolder;

	public NotifyApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 发送消息(需要管理员对应用有使用权限，对收件人touser、toparty、totag有查看权限，否则本次调用失败)
	 * <p>
	 * 1） 发送人员列表存在错误的userid：执行发送，开发者需注意返回结果说明</br>
	 * 2）发送人员不在通讯录权限范围内：不执行发送任务，返回首个出错的userid</br>
	 * 3）发送人员不在应用可见范围内：不执行发送任务，返回首个出错的userid</br>
	 * </p>
	 * 
	 * @param message
	 *            普通消息对象
	 * @return 如果无权限，则本次发送失败；如果收件人不存在或未关注，发送仍然执行。两种情况下均返回无效的部分</br> { "errcode":
	 *         0, "errmsg": "ok", "invaliduser": "UserID1",
	 *         "invalidparty":"PartyID1", "invalidtag":"TagID1" }
	 * @throws WeixinException
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E">发送接口说明</a>
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B%E5%8F%8A%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F">发送格式说明</a>
	 * @see com.foxinmy.weixin4j.tuple.Text
	 * @see com.foxinmy.weixin4j.tuple.Image
	 * @see com.foxinmy.weixin4j.tuple.Voice
	 * @see com.foxinmy.weixin4j.tuple.Video
	 * @see com.foxinmy.weixin4j.tuple.File
	 * @see com.foxinmy.weixin4j.tuple.News
	 * @see com.foxinmy.weixin4j.tuple.MpNews
	 * @see com.foxinmy.weixin4j.qy.message.NotifyMessage
	 * @see com.foxinmy.weixin4j.qy.model.IdParameter
	 */
	public IdParameter sendNotifyMessage(NotifyMessage message)
			throws WeixinException {
		NotifyTuple tuple = message.getTuple();
		Map<String, String> target = message.getTarget().getParameter();
		String msgtype = tuple.getMessageType();
		JSONObject obj = (JSONObject) JSON.toJSON(message);
		obj.put("msgtype", msgtype);
		obj.put(msgtype, tuple);
		if (target == null || target.isEmpty()) {
			obj.put("touser", "@all");
		} else {
			obj.putAll(target);
		}
		String message_send_uri = getRequestUri("message_send_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(message_send_uri, token.getAccessToken()),
				obj.toJSONString());
		obj = response.getAsJson();
		IdParameter idParameter = new IdParameter();
		if (obj.containsKey("invaliduser")) {
			idParameter.setUserIds(Arrays.asList(obj.getString("invalidlist")
					.split(IdParameter.SEPARATORS)));
		}
		if (obj.containsKey("invalidparty")) {
			List<Integer> partyIds = new ArrayList<Integer>();
			for (String id : obj.getString("invalidlist").split(
					IdParameter.SEPARATORS)) {
				partyIds.add(Integer.parseInt(id));
			}
			idParameter.setPartyIds(partyIds);
		}
		if (obj.containsKey("invalidtag")) {
			List<Integer> tagIds = new ArrayList<Integer>();
			for (String id : obj.getString("invalidtag").split(
					IdParameter.SEPARATORS)) {
				tagIds.add(Integer.parseInt(id));
			}
			idParameter.setPartyIds(tagIds);
		}
		return idParameter;
	}

	/**
	 * 发送客服消息
	 * 
	 * @param message
	 *            客服消息对象
	 * @return 发送结果
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%AE%A2%E6%9C%8D%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E">客服接口说明</a>
	 * @see com.foxinmy.weixin4j.tuple.Text
	 * @see com.foxinmy.weixin4j.tuple.Image
	 * @see com.foxinmy.weixin4j.tuple.Voice
	 * @see com.foxinmy.weixin4j.tuple.Video
	 * @see com.foxinmy.weixin4j.tuple.File
	 * @see com.foxinmy.weixin4j.qy.message.CustomeMessage
	 * @throws WeixinException
	 */
	public JsonResult sendCustomeMessage(CustomeMessage message)
			throws WeixinException {
		NotifyTuple tuple = message.getTuple();
		String msgtype = tuple.getMessageType();
		JSONObject obj = (JSONObject) JSON.toJSON(message);
		obj.put("msgtype", msgtype);
		obj.put(msgtype, tuple);
		String message_kf_send_uri = getRequestUri("message_kf_send_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(message_kf_send_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJsonResult();
	}

	/**
	 * 获取客服列表
	 * 
	 * @param kfType
	 *            客服类型 为空时返回全部类型的客服
	 * @return 第一个元素为内部客服(internal),第二个参数为外部客服(external)
	 * @see com.foxinmy.weixin4j.qy.model.IdParameter
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E4%BC%81%E4%B8%9A%E5%AE%A2%E6%9C%8D%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E">客服列表</a>
	 * @throws WeixinException
	 */
	public IdParameter[] getKfList(KfType kfType) throws WeixinException {
		String message_kf_list_uri = getRequestUri("message_kf_list_uri");
		if (kfType != null) {
			message_kf_list_uri += "&type=" + kfType.name();
		}
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				message_kf_list_uri, token.getAccessToken()));
		JSONObject obj = response.getAsJson();
		return new IdParameter[] {
				obj.containsKey("internal") ? obj.getObject("internal",
						IdParameter.class) : null,
				obj.containsKey("external") ? obj.getObject("external",
						IdParameter.class) : null };
	}
}
