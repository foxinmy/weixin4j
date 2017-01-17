package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.NameFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.message.TemplateMessage;
import com.foxinmy.weixin4j.mp.model.TemplateMessageInfo;
import com.foxinmy.weixin4j.mp.type.IndustryType;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.util.NameValue;

/**
 * 模板消息相关API
 * 
 * @className TemplApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月30日
 * @since JDK 1.6
 * @see
 */
public class TmplApi extends MpApi {

	private final TokenManager tokenManager;

	public TmplApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
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
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">设置所处行业</a>
	 */
	public ApiResult setTmplIndustry(IndustryType... industryTypes)
			throws WeixinException {
		JSONObject obj = new JSONObject();
		for (int i = 0; i < industryTypes.length; i++) {
			obj.put(String.format("industry_id%d", i + 1),
					Integer.toString(industryTypes[i].getTypeId()));
		}
		Token token = tokenManager.getCache();
		String template_set_industry_uri = getRequestUri("template_set_industry_uri");
		WeixinResponse response = weixinExecutor.post(String.format(
				template_set_industry_uri, token.getAccessToken()), obj
				.toJSONString());

		return response.getAsResult();
	}

	/**
	 * 获取设置的行业信息
	 * 
	 * @return 行业信息数组 第一个元素为帐号设置的主营行业 第二个元素为帐号设置的副营行业
	 * @throws WeixinException
	 * @see com.foxinmy.weixin4j.mp.type.IndustryType
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">获取设置的行业信息</a>
	 */
	public IndustryType[] getTmplIndustry() throws WeixinException {
		String template_get_industry_uri = getRequestUri("template_get_industry_uri");
		WeixinResponse response = weixinExecutor.get(String.format(
				template_get_industry_uri, tokenManager.getAccessToken()));
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
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">获得模板ID</a>
	 */
	public String getTemplateId(String shortId) throws WeixinException {
		Token token = tokenManager.getCache();
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
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">获取模板列表</a>
	 * @throws WeixinException
	 */
	public List<TemplateMessageInfo> getAllTemplates() throws WeixinException {
		Token token = tokenManager.getCache();
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
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">删除模板</a>
	 * @throws WeixinException
	 */
	public ApiResult deleteTemplate(String templateId) throws WeixinException {
		Token token = tokenManager.getCache();
		String template_del_uri = getRequestUri("template_del_uri");
		WeixinResponse response = weixinExecutor.post(
				String.format(template_del_uri, token.getAccessToken()),
				String.format("{\"template_id\":\"%s\"}", templateId));
		return response.getAsResult();
	}

	/**
	 * 发送模板消息
	 * 
	 * @param tplMessage
	 *            消息对象
	 * @return 发送的消息ID
	 * @throws WeixinException
	 * @see <a
	 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN">模板消息</a>
	 * @see <a href=
	 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751288&token=&lang=zh_CN"
	 *      >运营规范</a>
	 * @see com.foxinmy.weixin4j.mp.message.TemplateMessage
	 * @see com.foxinmy.weixin4j.msg.event.TemplatesendjobfinishMessage
	 */
	public String sendTmplMessage(TemplateMessage tplMessage)
			throws WeixinException {
		Token token = tokenManager.getCache();
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

		return response.getAsJson().getString("msgid");
	}
}
