package com.foxinmy.weixin4j.mp.api;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.message.NotifyMessage;
import com.foxinmy.weixin4j.msg.model.Notifyable;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 客服消息API
 * 
 * @className NotifyApi
 * @author jy.hu
 * @date 2014年9月26日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF">客服消息</a>
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
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF">发送客服消息</a>
	 * @see com.foxinmy.weixin4j.msg.model.Text
	 * @see com.foxinmy.weixin4j.msg.model.Image
	 * @see com.foxinmy.weixin4j.msg.model.Voice
	 * @see com.foxinmy.weixin4j.msg.model.Video
	 * @see com.foxinmy.weixin4j.msg.model.Music
	 * @see com.foxinmy.weixin4j.msg.model.News
	 * @see com.foxinmy.weixin4j.mp.message.NotifyMessage
	 */
	public JsonResult sendNotify(NotifyMessage notify) throws WeixinException {
		if (!(notify.getBox() instanceof Notifyable)) {
			throw new WeixinException("-1", String.format(
					"%s not implement Notifyable", notify.getBox().getClass()));
		}
		String custom_notify_uri = getRequestUri("custom_notify_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(
				String.format(custom_notify_uri, token.getAccessToken()),
				notify.toJson());

		return response.getAsJsonResult();
	}
}
