package com.foxinmy.weixin4j.qy.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.message.CustomeMessage;
import com.foxinmy.weixin4j.qy.message.NotifyMessage;
import com.foxinmy.weixin4j.qy.model.IdParameter;
import com.foxinmy.weixin4j.qy.type.KfType;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.tuple.MpNews;
import com.foxinmy.weixin4j.tuple.NotifyTuple;

/**
 * 客服消息API
 *
 * @className NotifyApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月21日
 * @since JDK 1.6
 * @see <a href="https://work.weixin.qq.com/api/doc#10167">发送接口说明</a>
 * @see com.foxinmy.weixin4j.tuple.Text
 * @see com.foxinmy.weixin4j.tuple.Image
 * @see com.foxinmy.weixin4j.tuple.Voice
 * @see com.foxinmy.weixin4j.tuple.Video
 * @see com.foxinmy.weixin4j.tuple.File
 * @see com.foxinmy.weixin4j.tuple.News
 * @see com.foxinmy.weixin4j.tuple.MpNews
 */
public class NotifyApi extends QyApi {

	private final TokenManager tokenManager;

	public NotifyApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 发送消息提醒(需要管理员对应用有使用权限，对收件人touser、toparty、totag有查看权限，否则本次调用失败)
	 * <p>
	 * 1） 发送人员列表存在错误的userid：执行发送，开发者需注意返回结果说明</br>
	 * 2）发送人员不在通讯录权限范围内：不执行发送任务，返回首个出错的userid</br>
	 * 3）发送人员不在应用可见范围内：不执行发送任务，返回首个出错的userid</br>
	 * </p>
	 *
	 * @param message
	 *            消息对象
	 * @return 如果无权限或收件人不存在，则本次发送失败；如果未关注，发送仍然执行。两种情况下均返回无效的部分（注：由于userid不区分大小写，
	 *         返回的列表都统一转为小写</br> { "errcode": 0, "errmsg": "ok", "invaliduser":
	 *         "UserID1", "invalidparty":"PartyID1", "invalidtag":"TagID1" }
	 * @throws WeixinException
	 * @see <a href="https://work.weixin.qq.com/api/doc#10167">发送接口说明</a>
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
		if (tuple instanceof MpNews) {
			if (((MpNews) tuple).getArticles().isEmpty()) {
				throw new WeixinException("notify fail:articles is required");
			}
		}
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
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(message_send_uri, token.getAccessToken()),
				obj.toJSONString());
		obj = response.getAsJson();
		IdParameter idParameter = IdParameter.get();
		if (obj.containsKey("invaliduser")) {
			idParameter.setUserIds(Arrays.asList(obj.getString("invaliduser")
					.split(IdParameter.SEPARATORS)));
		}
		if (obj.containsKey("invalidparty")) {
			List<Integer> partyIds = new ArrayList<Integer>();
			for (String id : obj.getString("invalidparty").split(
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
			idParameter.setTagIds(tagIds);
		}
		return idParameter;
	}

	/**
	 * 发送客服消息
	 *
	 * @param message
	 *            消息对象
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
	public ApiResult sendCustomeMessage(CustomeMessage message)
			throws WeixinException {
		NotifyTuple tuple = message.getTuple();
		String msgtype = tuple.getMessageType();
		JSONObject obj = (JSONObject) JSON.toJSON(message);
		obj.put("msgtype", msgtype);
		obj.put(msgtype, tuple);
		String message_kf_send_uri = getRequestUri("message_kf_send_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(message_kf_send_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 获取客服列表
	 *
	 * @param kfType
	 *            客服类型 为空时返回全部类型的客服
	 * @return 第一个元素为内部客服(internal),第二个元素为外部客服(external)
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
		Token token = tokenManager.getCache();
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
