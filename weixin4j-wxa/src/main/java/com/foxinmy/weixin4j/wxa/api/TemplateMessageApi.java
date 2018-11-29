package com.foxinmy.weixin4j.wxa.api;

import java.util.Map;
import java.util.Properties;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 发送模板消息。
 *
 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/template-message.html">发送模板消息</a>
 * @since 1.8
 */
public class TemplateMessageApi extends TokenManagerApi {

	public TemplateMessageApi(TokenManager tokenManager) {
		this(tokenManager, null);
	}

	public TemplateMessageApi(TokenManager tokenManager, Properties properties) {
		super(tokenManager, properties);
	}

	/**
	 * 发送模板消息
	 *
	 * @param toUser 接收者（用户）的 openid。
	 * @param templateId 所需下发的模板消息的 id。
	 * @param page 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
	 * @param formId 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id。
	 * @param data 模板内容，不填则下发空模板。
	 * @param emphasisKeyword 模板需要放大的关键词，不填则默认无放大。
	 * @throws WeixinException indicates getting access token failed, or sending template message failed.
	 * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/sendTemplateMessage.html">发送模板消息</a>
	 */
	public void sendTemplateMessage(
		final String toUser,
		final String templateId,
		final String page,
		final String formId,
		final Map<String, String> data,
		final String emphasisKeyword
	) throws WeixinException {
		final TemplateMessageParameter message = new TemplateMessageParameter(
			toUser, templateId, page, formId, data, emphasisKeyword
		);
		final WxaApiResult r = this.post(
			"wxopen_template_message_send",
			message,
			WxaApiResult.TYPE_REFERENCE
		);
		r.checkErrCode();
	}

}
