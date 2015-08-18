package com.foxinmy.weixin4j.mp.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.mp.type.IndustryType;
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
	 * 设置所属行业(每月可修改行业1次，账号仅可使用所属行业中相关的模板)
	 * 
	 * @param industryType
	 *            所处行业 目前不超过两个
	 * @return 操作结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.type.IndustryType
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html#.E8.AE.BE.E7.BD.AE.E6.89.80.E5.B1.9E.E8.A1.8C.E4.B8.9A">设置所处行业</a>
	 */
	public JsonResult setTmplIndustry(IndustryType... industryType)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		for (int i = 0; i < industryType.length; i++) {
			obj.put(String.format("industry_id%d", i + 1),
					String.valueOf(industryType[i].getValue()));
		}
		Token token = tokenHolder.getToken();
		String template_set_industry_uri = getRequestUri("template_set_industry_uri");
		WeixinResponse response = weixinExecutor.post(String.format(
				template_set_industry_uri, token.getAccessToken()), obj
				.toJSONString());

		return response.getAsJsonResult();
	}

	/**
	 * 获取模板ID
	 * 
	 * @param shortId
	 *            模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return 模板ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html#.E8.8E.B7.E5.BE.97.E6.A8.A1.E6.9D.BFID">获得模板ID</a>
	 */
	public String getTemplateId(String shortId) throws WeixinException {
		Token token = tokenHolder.getToken();
		String template_getid_uri = getRequestUri("template_getid_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(template_getid_uri, token.getAccessToken()),
				String.format("{\"template_id_short\":\"%s\"}", shortId));

		return response.getAsJson().getString("template_id");
	}

	/**
	 * 发送模板消息
	 * 
	 * @param tplMessage
	 *            消息对象
	 * @return 发送结果
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html#.E5.8F.91.E9.80.81.E6.A8.A1.E6.9D.BF.E6.B6.88.E6.81.AF">模板消息</a>
	 *      <a href=
	 *      "http://mp.weixin.qq.com/wiki/2/def71e3ecb5706c132229ae505815966.html"
	 *      >运营规范</a>
	 * @see com.foxinmy.weixin4j.mp.message.TemplateMessage
	 * @seee com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
	 */
	public JsonResult sendTmplMessage(TemplateMessage tplMessage)
			throws WeixinException {
		Token token = tokenHolder.getToken();
		String template_send_uri = getRequestUri("template_send_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(template_send_uri, token.getAccessToken()),
				JSON.toJSONString(tplMessage));

		return response.getAsJsonResult();
	}
}
