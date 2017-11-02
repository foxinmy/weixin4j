package com.foxinmy.weixin4j.qy.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.NameFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.type.ButtonType;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 菜单相关API
 *
 * @className MenuApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.model.Button
 */
public class MenuApi extends QyApi {

	private final TokenManager tokenManager;

	public MenuApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 自定义菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式)
	 *
	 * @param agentid
	 *            应用ID
	 *
	 * @param buttons
	 *            菜单列表
	 * @throws WeixinException
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10786"> 创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public ApiResult createMenu(int agentid, List<Button> buttons)
			throws WeixinException {
		String menu_create_uri = getRequestUri("menu_create_uri");
		Token token = tokenManager.getCache();
		JSONObject obj = new JSONObject();
		obj.put("button", buttons);
		WeixinResponse response = weixinExecutor
				.post(String.format(menu_create_uri, token.getAccessToken(),
						agentid), JSON.toJSONString(obj, new NameFilter() {
					@Override
					public String process(Object object, String name,
							Object value) {
						if (object instanceof Button
								&& name.equals("content")
								&& StringUtil.isNotBlank(((Button) object)
										.getType())) {
							ButtonType buttonType = ButtonType
									.valueOf(((Button) object).getType());
							if (ButtonType.view == buttonType) {
								return "url";
							} else if (ButtonType.media_id == buttonType
									|| ButtonType.view_limited == buttonType) {
								return "media_id";
							} else {
								return "key";
							}
						}
						return name;
					}
				}));

		return response.getAsResult();
	}

	/**
	 * 查询菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式。)
	 *
	 * @param agentid
	 *            应用ID
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10787"> 查询菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public List<Button> getMenu(int agentid) throws WeixinException {
		String menu_get_uri = getRequestUri("menu_get_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				menu_get_uri, token.getAccessToken(), agentid));
		JSONArray buttons = response.getAsJson().getJSONArray("button");
		List<Button> buttonList = new ArrayList<Button>(buttons.size());
		ParseProcess processor = new ExtraProcessor() {
			@Override
			public void processExtra(Object object, String key, Object value) {
				((Button) object).setContent(String.valueOf(value));
			}
		};
		for (int i = 0; i < buttons.size(); i++) {
			buttonList.add(JSON.parseObject(buttons.getString(i), Button.class,
					processor));
		}
		return buttonList;
	}

	/**
	 * 删除菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式)
	 *
	 * @param agentid
	 *            应用ID
	 * @throws WeixinException
	 * @see <a href= "https://work.weixin.qq.com/api/doc#10788"> 删除菜单</a>
	 * @return 处理结果
	 */
	public ApiResult deleteMenu(int agentid) throws WeixinException {
		String menu_delete_uri = getRequestUri("menu_delete_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				menu_delete_uri, token.getAccessToken(), agentid));

		return response.getAsResult();
	}
}
