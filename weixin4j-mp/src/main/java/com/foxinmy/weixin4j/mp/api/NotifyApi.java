package com.foxinmy.weixin4j.mp.api;

import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.message.NotifyMessage;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.tuple.NotifyTuple;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 客服消息API
 * 
 * @className NotifyApi
 * @author jy.hu
 * @date 2014年9月26日
 * @since JDK 1.6
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.8F.A3-.E5.8F.91.E6.B6.88.E6.81.AF">客服消息</a>
 * @see com.foxinmy.weixin4j.mp.message.NotifyMessage
 */
public class NotifyApi extends MpApi {

	private final TokenHolder tokenHolder;

	public NotifyApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 发送客服消息(在48小时内不限制发送次数)
	 * 
	 * @param notify
	 *            客服消息对象
	 * @return 处理结果
	 * @see {@link #sendNotify(NotifyMessage, String)}
	 * @throws WeixinException
	 */
	public JsonResult sendNotify(NotifyMessage notify) throws WeixinException {
		return sendNotify(notify, null);
	}

	/**
	 * 发送客服消息(在48小时内不限制发送次数)
	 * 
	 * @param notify
	 *            客服消息对象
	 * @param kfAccount
	 *            客服账号 可为空
	 * @throws WeixinException
	 * @return 处理结果
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E5.AE.A2.E6.9C.8D.E6.8E.A5.E5.8F.A3-.E5.8F.91.E6.B6.88.E6.81.AF">发送客服消息</a>
	 * @see com.foxinmy.weixin4j.tuple.Text
	 * @see com.foxinmy.weixin4j.tuple.Image
	 * @see com.foxinmy.weixin4j.tuple.Voice
	 * @see com.foxinmy.weixin4j.tuple.Video
	 * @see com.foxinmy.weixin4j.tuple.Music
	 * @see com.foxinmy.weixin4j.tuple.News
	 * @see com.foxinmy.weixin4j.mp.message.NotifyMessage
	 */
	public JsonResult sendNotify(NotifyMessage notify, String kfAccount)
			throws WeixinException {
		NotifyTuple tuple = notify.getTuple();
		String msgtype = tuple.getMessageType();
		if ("mpnews".equals(msgtype)) {
			throw new WeixinException("only support news message");
		}
		JSONObject obj = new JSONObject();
		obj.put("touser", notify.getTouser());
		obj.put("msgtype", msgtype);
		obj.put(msgtype, tuple);
		if (StringUtil.isNotBlank(kfAccount)) {
			JSONObject kf = new JSONObject();
			kf.put("kf_account", kfAccount);
			obj.put("customservice", kf);
		}
		String custom_notify_uri = getRequestUri("custom_notify_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.post(
				String.format(custom_notify_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJsonResult();
	}
}
