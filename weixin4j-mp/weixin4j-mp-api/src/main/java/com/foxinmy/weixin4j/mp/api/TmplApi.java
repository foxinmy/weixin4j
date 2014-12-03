package com.foxinmy.weixin4j.mp.api;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 模板消息相关API
 * 
 * @className TemplApi
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see
 */
public class TmplApi extends MpApi {

	private final TokenHolder tokenHolder;

	public TmplApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 发送模板消息
	 * 
	 * @param message 消息对象
	 * @return 发送结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%A8%A1%E6%9D%BF%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3">模板消息</a>
	 * @see com.foxinmy.weixin4j.mp.message.TemplateMessage
	 * @seee com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
	 */
	public JsonResult sendTmplMessage(TemplateMessage tplMessage)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String template_send_uri = getRequestUri("template_send_uri");
		Response response = request.post(
				String.format(template_send_uri, token.getAccessToken()), JSON.toJSONString(tplMessage));

		return response.getAsJsonResult();
	}
}
