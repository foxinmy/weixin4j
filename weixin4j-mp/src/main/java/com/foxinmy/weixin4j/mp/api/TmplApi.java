package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.NameFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.mp.model.TemplateMessageInfo;
import com.foxinmy.weixin4j.mp.type.IndustryType;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.util.NameValue;

/**
 * 模板消息相关API
 * 
 * @className TemplApi
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.6
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
	 * @param industryTypes
	 *            所处行业 目前不超过两个
	 * @return 操作结果
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.type.IndustryType
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E8.AE.BE.E7.BD.AE.E6.89.80.E5.B1.9E.E8.A1.8C.E4.B8.9A">设置所处行业</a>
	 */
	public JsonResult setTmplIndustry(IndustryType... industryTypes)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		for (int i = 0; i < industryTypes.length; i++) {
			obj.put(String.format("industry_id%d", i + 1),
					Integer.toString(industryTypes[i].getTypeId()));
		}
		Token token = tokenHolder.getToken();
		String template_set_industry_uri = getRequestUri("template_set_industry_uri");
		WeixinResponse response = weixinExecutor.post(String.format(
				template_set_industry_uri, token.getAccessToken()), obj
				.toJSONString());

		return response.getAsJsonResult();
	}

	/**
	 * 获取设置的行业信息
	 * 
	 * @return 行业信息数组 第一个元素为帐号设置的主营行业 第二个元素为帐号设置的副营行业
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.type.IndustryType
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E8.8E.B7.E5.8F.96.E8.AE.BE.E7.BD.AE.E7.9A.84.E8.A1.8C.E4.B8.9A.E4.BF.A1.E6.81.AF">获取设置的行业信息</a>
	 */
	public IndustryType[] getTmplIndustry() throws WeixinException {
		String template_get_industry_uri = getRequestUri("template_get_industry_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				template_get_industry_uri, tokenHolder.getAccessToken()));
		JSONObject primary = response.getAsJson().getJSONObject(
				"primary_industry");
		JSONObject secondary = response.getAsJson().getJSONObject(
				"secondary_industry");
		return new IndustryType[] {
				primary != null ? IndustryType.getIndustry(
						primary.getString("first_class"),
						primary.getString("second_class")) : null,
				secondary != null ? IndustryType.getIndustry(
						secondary.getString("first_class"),
						secondary.getString("second_class")) : null };
	}

	/**
	 * 获取模板ID
	 * 
	 * @param shortId
	 *            模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return 模板ID
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E8.8E.B7.E5.BE.97.E6.A8.A1.E6.9D.BFID">获得模板ID</a>
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
	 * 获取模板列表
	 * 
	 * @return 模板列表
	 * @see com.foxinmy.weixin4j.mp.model.TemplateMessageInfo
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E8.8E.B7.E5.8F.96.E6.A8.A1.E6.9D.BF.E5.88.97.E8.A1.A8">获取模板列表</a>
	 * @throws WeixinException
	 */
	public List<TemplateMessageInfo> getAllTemplates() throws WeixinException {
		Token token = tokenHolder.getToken();
		String template_getall_uri = getRequestUri("template_getall_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				template_getall_uri, token.getAccessToken()));
		return JSON.parseArray(response.getAsJson().getString("template_list"),
				TemplateMessageInfo.class);
	}

	/**
	 * 删除模板
	 * 
	 * @param templateId
	 *            公众帐号下模板消息ID
	 * @return 处理结果
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/5/6dde9eaa909f83354e0094dc3ad99e05.html#.E5.88.A0.E9.99.A4.E6.A8.A1.E6.9D.BF">删除模板</a>
	 * @throws WeixinException
	 */
	public JsonResult deleteTemplate(String templateId) throws WeixinException {
		Token token = tokenHolder.getToken();
		String template_del_uri = getRequestUri("template_del_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(template_del_uri, token.getAccessToken()),
				String.format("{\"template_id\"=\"%s\"}", templateId));
		return response.getAsJsonResult();
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
	 * @see <a href=
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
				JSON.toJSONString(tplMessage, new NameFilter() {
					@Override
					public String process(Object object, String name,
							Object value) {
						if (object instanceof NameValue && name.equals("name")) {
							return "color";
						}
						return name;
					}
				}));

		return response.getAsJsonResult();
	}
}
