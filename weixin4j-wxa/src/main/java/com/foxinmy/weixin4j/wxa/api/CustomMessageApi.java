package com.foxinmy.weixin4j.wxa.api;

import java.util.Map;
import java.util.Properties;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.wxa.model.custommessage.Command;
import com.foxinmy.weixin4j.wxa.model.custommessage.CustomMessage;

/**
 * 客服消息。
 *
 * @since 1.8
 */
public class CustomMessageApi extends TokenManagerApi {

	public CustomMessageApi(TokenManager tokenManager) {
		super(tokenManager);
	}

	public CustomMessageApi(TokenManager tokenManager, Properties properties) {
		super(tokenManager, properties);
	}

	/**
	 * 发送客服消息。
	 *
	 * <p>
	 *   当用户和小程序客服产生特定动作的交互时（具体动作列表请见下方说明），
	 *   微信将会把消息数据推送给开发者，开发者可以在一段时间内（目前修改为48小时）调用客服接口，
	 *   通过POST一个JSON数据包来发送消息给普通用户。
	 *   此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
	 * </p>
	 *
	 * @param customMessage the {@link CustomMessage}.
	 * @throws WeixinException indicates getting access token failed, or sending custom message failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/customer-message/send.html">发送客服消息</a>
	 */
	public void sendCustomMessage(final CustomMessage customMessage)
			throws WeixinException {
		final Map<String, Object> params = CustomMessageAdapters.toMap(customMessage);
		final WxaApiResult r = this.post("message_custom_send",
			params, WxaApiResult.TYPE_REFERENCE);
		r.checkErrCode();
	}

	/**
	 * 客服输入状态。
	 *
	 * <p>开发者可通过调用“客服输入状态接口”，返回客服当前输入状态给用户。</p>
	 *
	 * <ol>
	 *   <li>此接口需要客服消息接口权限。</li>
	 *   <li>如果不满足发送客服消息的触发条件，则无法下发输入状态。</li>
	 *   <li>下发输入状态，需要客服之前30秒内跟用户有过消息交互。</li>
	 *   <li>在输入状态中（持续15s），不可重复下发输入态。</li>
	 *   <li>在输入状态中，如果向用户下发消息，会同时取消输入状态。</li>
	 * </ol>
	 *
	 * @param toUser 普通用户(openid)
	 * @param command "Typing"：对用户下发“正在输入"状态；"CancelTyping"：取消对用户的”正在输入"状态
	 * @throws WeixinException indicates getting access token failed, or sending typing status failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/customer-message/typing.html">客服输入状态</a>
	 */
	public void typingCustomMessage(String toUser, Command command)
			throws WeixinException {
		final Map<String, String> params = CustomMessageAdapters.toMap(toUser, command);
		final WxaApiResult r = this.post("message_custom_typing",
			params, WxaApiResult.TYPE_REFERENCE);
		r.checkErrCode();
	}

}
