package com.foxinmy.weixin4j.wxa.api;

import java.util.Map;
import java.util.Properties;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 发送订阅消息。
 *
 * <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html">发送订阅消息</a>
 * @since 1.9
 */
public class SubscribeMessageApi extends TokenManagerApi {

	public SubscribeMessageApi(TokenManager tokenManager) {
		super(tokenManager, null);
	}

	public SubscribeMessageApi(TokenManager tokenManager, Properties properties) {
		super(tokenManager, properties);
	}
	/**
	 * 发送订阅消息
	 *
	 * @param toUser 接收者（用户）的 openid。
	 * @param templateId 所需下发的订阅模板id
	 * @param page 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
	 * @param data 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
	 * @throws WeixinException indicates getting access token failed, or sending template message failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html">发送订阅消息</a>
	 */
	public void sendSubscribeMessage(
		String toUser,
		String templateId,
		String page,
		Map<String, String> data
	) throws WeixinException {

		final SubscribeMessageParameter message = new SubscribeMessageParameter(
			toUser, templateId, page, data
		);
		WxaApiResult r = this.post("wxopen_subscribe_message_send", message, WxaApiResult.TYPE_REFERENCE);
		r.checkErrCode();
	}

}
