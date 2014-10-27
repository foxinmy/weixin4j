package com.foxinmy.weixin4j.api;

import com.foxinmy.weixin4j.api.token.TokenApi;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.BaseResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.msg.out.TemplateMessage;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 模板消息相关API
 * 
 * @className TemplApi
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see
 */
public class TmplApi extends BaseApi {

	private final TokenApi tokenApi;

	public TmplApi(TokenApi tokenApi) {
		this.tokenApi = tokenApi;
	}

	/**
	 * 发送模板消息
	 * 
	 * @param message
	 * @return 发送结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E6%A8%A1%E6%9D%BF%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3">模板消息</a>
	 * @see com.foxinmy.weixin4j.msg.out.TemplateMessage
	 * @seee com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
	 */
	public BaseResult sendTmplMessage(TemplateMessage tplMessage)
			throws WeixinException {
		Token token = tokenApi.getToken();
		String template_send_uri = ConfigUtil.getValue("template_send_uri");
		Response response = request.post(
				String.format(template_send_uri, token.getAccessToken()),
				tplMessage.toJson());

		return response.getAsResult();
	}
}
