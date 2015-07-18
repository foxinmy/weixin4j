package com.foxinmy.weixin4j.qy.api;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.message.NotifyMessage;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.tuple.NotifyTuple;

/**
 * 发送消息API
 * 
 * @className NotifyApi
 * @author jy.hu
 * @date 2014年11月21日
 * @since JDK 1.7
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
	 * @param notify
	 *            客服消息对象
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
	 */
	public JSONObject sendNotify(NotifyMessage notify) throws WeixinException {
		NotifyTuple tuple = notify.getTuple();
		Map<String, String> target = notify.getTarget().getParameter();
		String msgtype = tuple.getMessageType();
		JSONObject obj = (JSONObject) JSON.toJSON(notify);
		obj.put("msgtype", msgtype);
		obj.put(msgtype, tuple);
		if (target == null || target.isEmpty()) {
			obj.put("touser", "@all");
		} else {
			obj.putAll(target);
		}
		String message_send_uri = getRequestUri("message_send_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinClient.post(
				String.format(message_send_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJson();
	}
}
