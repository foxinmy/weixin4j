package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.NameFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.foxinmy.weixin4j.type.ButtonType;

/**
 * 菜单相关API
 * 
 * @className MenuApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.model.Button
 */
public class MenuApi extends MpApi {

	private final TokenHolder tokenHolder;

	public MenuApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 自定义菜单
	 * 
	 * @param btnList
	 *            菜单列表
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html">创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public JsonResult createMenu(List<Button> btnList) throws WeixinException {
		String menu_create_uri = getRequestUri("menu_create_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("button", btnList);
		WeixinResponse response = weixinExecutor.post(
				String.format(menu_create_uri, token.getAccessToken()),
				JSON.toJSONString(obj, new NameFilter() {
					@Override
					public String process(Object object, String name,
							Object value) {
						if (object instanceof Button && name.equals("content")) {
							ButtonType buttonType = ((Button) object).getType();
							if (buttonType != null) {
								if (ButtonType.view == buttonType) {
									return "url";
								} else if (ButtonType.media_id == buttonType
										|| ButtonType.view_limited == buttonType) {
									return "media_id";
								} else {
									return "key";
								}
							}
						}
						return name;
					}
				}));

		return response.getAsJsonResult();
	}

	/**
	 * 查询菜单
	 * 
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html">查询菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public List<Button> getMenu() throws WeixinException {
		String menu_get_uri = getRequestUri("menu_get_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				menu_get_uri, token.getAccessToken()));

		JSONArray buttons = response.getAsJson().getJSONObject("menu")
				.getJSONArray("button");
		List<Button> buttonList = new ArrayList<Button>(buttons.size());
		ParseProcess processor = new ExtraProcessor() {
			@Override
			public void processExtra(Object object, String key, Object value) {
				JSONPath.set(object, "$.content", value);
			}
		};
		for (int i = 0; i < buttons.size(); i++) {
			buttonList.add(JSON.parseObject(buttons.getString(i), Button.class,
					processor));
		}
		return buttonList;
	}

	/**
	 * 删除菜单
	 * 
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/16/8ed41ba931e4845844ad6d1eeb8060c8.html">删除菜单</a>
	 * @return 处理结果
	 */
	public JsonResult deleteMenu() throws WeixinException {
		String menu_delete_uri = getRequestUri("menu_delete_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				menu_delete_uri, token.getAccessToken()));

		return response.getAsJsonResult();
	}
}
