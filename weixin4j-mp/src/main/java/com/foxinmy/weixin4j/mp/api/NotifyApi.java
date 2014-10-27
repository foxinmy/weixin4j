package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.BaseResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.model.CustomRecord;
import com.foxinmy.weixin4j.mp.msg.model.Article;
import com.foxinmy.weixin4j.mp.msg.model.BaseMsg;
import com.foxinmy.weixin4j.mp.msg.notify.ArticleNotify;
import com.foxinmy.weixin4j.mp.msg.notify.BaseNotify;
import com.foxinmy.weixin4j.token.TokenApi;
import com.foxinmy.weixin4j.util.ConfigUtil;

/**
 * 客服相关API
 * 
 * @className NotifyApi
 * @author jy.hu
 * @date 2014年9月26日
 * @since JDK 1.7
 * @see <a
 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF">客服消息</a>
 * @see com.foxinmy.weixin4j.mp.msg.notify.TextNotify
 * @see com.foxinmy.weixin4j.mp.msg.notify.ImageNotify
 * @see com.foxinmy.weixin4j.mp.msg.notify.MusicNotify
 * @see com.foxinmy.weixin4j.mp.msg.notify.VideoNotify
 * @see com.foxinmy.weixin4j.mp.msg.notify.VoiceNotify
 * @see com.foxinmy.weixin4j.mp.msg.notify.ArticleNotify
 */
public class NotifyApi extends BaseApi {

	private final TokenApi tokenApi;

	public NotifyApi(TokenApi tokenApi) {
		this.tokenApi = tokenApi;
	}

	/**
	 * 发送客服消息
	 * 
	 * @param jsonPara
	 * @return
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E9.9F.B3.E4.B9.90.E6.B6.88.E6.81.AF">发送客服消息</a>
	 */
	private BaseResult sendNotify(String jsonPara) throws WeixinException {
		String custom_notify_uri = ConfigUtil.getValue("custom_notify_uri");
		Token token = tokenApi.getToken();
		Response response = request.post(
				String.format(custom_notify_uri, token.getAccessToken()),
				jsonPara);

		return response.getAsResult();
	}

	/**
	 * 发送客服消息(在48小时内不限制发送次数)
	 * 
	 * @param notify
	 *            客服消息对象
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF">发送客服消息</a>
	 * @see com.foxinmy.weixin4j.mp.msg.notify.TextNotify
	 * @see com.foxinmy.weixin4j.mp.msg.notify.ImageNotify
	 * @see com.foxinmy.weixin4j.mp.msg.notify.MusicNotify
	 * @see com.foxinmy.weixin4j.mp.msg.notify.VideoNotify
	 * @see com.foxinmy.weixin4j.mp.msg.notify.VoiceNotify
	 * @see com.foxinmy.weixin4j.mp.msg.notify.ArticleNotify
	 * @see {@link com.foxinmy.weixin4j.mp.api.NotifyApi#sendNotify(String)}
	 */
	public BaseResult sendNotify(BaseNotify notify) throws WeixinException {
		return sendNotify(notify.toJson());
	}

	/**
	 * 发送图文消息
	 * 
	 * @param touser
	 *            目标ID
	 * @param articles
	 *            图文列表
	 * @return 发送结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.msg.model.Article
	 * @see com.foxinmy.weixin4j.mp.msg.notify.ArticleNotify
	 */
	public BaseResult sendNotify(String touser, List<Article> articles)
			throws WeixinException {
		ArticleNotify notify = new ArticleNotify(touser);
		notify.pushAll(articles);
		return sendNotify(notify);
	}

	/**
	 * 发送客服消息(不包含图文消息)
	 * 
	 * @param touser
	 *            目标用户
	 * @param baseMsg
	 *            消息类型
	 * @return 发送结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.msg.model.Text
	 * @see com.foxinmy.weixin4j.mp.msg.model.Image
	 * @see com.foxinmy.weixin4j.mp.msg.model.Music
	 * @see com.foxinmy.weixin4j.mp.msg.model.Video
	 * @see com.foxinmy.weixin4j.mp.msg.model.Voice
	 * @see {@link com.foxinmy.weixin4j.mp.msg.model.BaseMsg#toNotifyJson()}
	 * @see {@link com.foxinmy.weixin4j.mp.api.NotifyApi#sendNotify(String)}
	 */
	public BaseResult sendNotify(String touser, BaseMsg baseMsg)
			throws WeixinException {
		StringBuilder jsonPara = new StringBuilder();
		String mediaType = baseMsg.getMediaType().name();
		jsonPara.append("{");
		jsonPara.append("\"touser\":\"").append(touser).append("\",");
		jsonPara.append("\"msgtype\":\"").append(mediaType).append("\",");
		jsonPara.append("\"").append(mediaType).append("\":");
		jsonPara.append(baseMsg.toNotifyJson()).append("}");

		return sendNotify(jsonPara.toString());
	}

	/**
	 * 客服聊天记录
	 * 
	 * @param openId
	 *            用户标识 可为空
	 * @param starttime
	 *            查询开始时间
	 * @param endtime
	 *            查询结束时间 每次查询不能跨日查询
	 * @param pagesize
	 *            每页大小 每页最多拉取1000条
	 * @param pageindex
	 *            查询第几页 从1开始
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.model.CustomRecord
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E5%AE%A2%E6%9C%8D%E8%81%8A%E5%A4%A9%E8%AE%B0%E5%BD%95">查询客服聊天记录</a>
	 */
	public List<CustomRecord> getCustomRecord(String openId, long starttime,
			long endtime, int pagesize, int pageindex) throws WeixinException {
		JSONObject obj = new JSONObject();
		obj.put("openId", openId == null ? "" : openId);
		obj.put("starttime", starttime);
		obj.put("endtime", endtime);
		obj.put("pagesize", pagesize > 1000 ? 1000 : pagesize);
		obj.put("pageindex", pageindex);
		String custom_record_uri = ConfigUtil.getValue("custom_record_uri");
		Token token = tokenApi.getToken();
		Response response = request.post(
				String.format(custom_record_uri, token.getAccessToken()),
				obj.toJSONString());

		String text = response.getAsJson().getString("recordlist");
		return JSON.parseArray(text, CustomRecord.class);
	}
}
