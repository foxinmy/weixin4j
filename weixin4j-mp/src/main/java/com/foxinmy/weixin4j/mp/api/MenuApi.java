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
import com.foxinmy.weixin4j.mp.model.Menu;
import com.foxinmy.weixin4j.mp.model.MenuMatchRule;
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
 * @see http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
 */
public class MenuApi extends MpApi {

	private final TokenHolder tokenHolder;

	public MenuApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 自定义菜单
	 * 
	 * @param buttons
	 *            菜单列表
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html">创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public JsonResult createMenu(List<Button> buttons) throws WeixinException {
		String menu_create_uri = getRequestUri("menu_create_uri");
		JSONObject obj = new JSONObject();
		obj.put("button", buttons);
		return createMenu0(menu_create_uri, obj);
	}

	private JsonResult createMenu0(String url, JSONObject data)
			throws WeixinException {
		WeixinResponse response = weixinExecutor.post(
				String.format(url, tokenHolder.getAccessToken()),
				JSON.toJSONString(data, new NameFilter() {
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
		return buttonsConvertor(getMenu0().getJSONObject("menu"));
	}

	private JSONObject getMenu0() throws WeixinException {
		String menu_get_uri = getRequestUri("menu_get_uri");
		Token token = tokenHolder.getToken();
		WeixinResponse response = weixinExecutor.get(String.format(
				menu_get_uri, token.getAccessToken()));
		return response.getAsJson();
	}

	/**
	 * 查询全部菜单(包含个性化菜单)
	 * 
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/16/ff9b7b85220e1396ffa16794a9d95adc.html">查询菜单</a>
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html">个性化菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @see com.foxinmy.weixin4j.mp.model.Menu
	 */
	public List<Menu> getAllMenu() throws WeixinException {
		JSONObject response = getMenu0();
		List<Menu> menus = new ArrayList<Menu>();
		// 普通菜单
		JSONObject menuObj = response.getJSONObject("menu");
		menus.add(new Menu(menuObj.getString("menuid"),
				buttonsConvertor(menuObj), null));
		// 个性化菜单
		JSONArray menuObjs = response.getJSONArray("conditionalmenu");
		if (menuObjs != null && !menuObjs.isEmpty()) {
			for (int i = 0; i < menuObjs.size(); i++) {
				menuObj = menuObjs.getJSONObject(i);
				menus.add(new Menu(menuObj.getString("menuid"),
						buttonsConvertor(menuObj), menuObj.getObject(
								"matchrule", MenuMatchRule.class)));
			}
		}
		return menus;
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

	/**
	 * 创建个性化菜单
	 * 
	 * @param buttons
	 *            菜单列表
	 * @param matchRule
	 *            匹配规则 至少要有一个匹配信息是不为空
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html#.E5.88.9B.E5.BB.BA.E4.B8.AA.E6.80.A7.E5.8C.96.E8.8F.9C.E5.8D.95">创建个性化菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public JsonResult createCustomMenu(List<Button> buttons,
			MenuMatchRule matchRule) throws WeixinException {
		String menu_create_uri = getRequestUri("menu_custom_create_uri");
		JSONObject obj = new JSONObject();
		obj.put("button", buttons);
		obj.put("matchrule", matchRule.getRule());
		return createMenu0(menu_create_uri, obj);
	}

	/**
	 * 删除个性化菜单
	 * 
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html#.E5.88.A0.E9.99.A4.E4.B8.AA.E6.80.A7.E5.8C.96.E8.8F.9C.E5.8D.95">删除个性化菜单</a>
	 * @return 处理结果
	 */
	public JsonResult deleteCustomMenu(String menuId) throws WeixinException {
		String menu_delete_uri = getRequestUri("menu_delete_custom_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("menuid", menuId);
		WeixinResponse response = weixinExecutor.post(
				String.format(menu_delete_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJsonResult();
	}

	/**
	 * 测试个性化菜单匹配结果
	 * 
	 * @param userId
	 *            可以是粉丝的OpenID，也可以是粉丝的微信号。
	 * @return 匹配到的菜单配置
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html#.E6.B5.8B.E8.AF.95.E4.B8.AA.E6.80.A7.E5.8C.96.E8.8F.9C.E5.8D.95.E5.8C.B9.E9.85.8D.E7.BB.93.E6.9E.9C">测试个性化菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 * @throws WeixinException
	 */
	public List<Button> matchCustomMenu(String userId) throws WeixinException {
		String menu_trymatch_uri = getRequestUri("menu_trymatch_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("user_id", userId);
		WeixinResponse response = weixinExecutor.post(
				String.format(menu_trymatch_uri, token.getAccessToken()),
				obj.toJSONString());

		return buttonsConvertor(response.getAsJson().getJSONObject("menu"));
	}

	private final ParseProcess buttonProcess = new ExtraProcessor() {
		@Override
		public void processExtra(Object object, String key, Object value) {
			JSONPath.set(object, "$.content", value);
		}
	};

	private List<Button> buttonsConvertor(JSONObject menu) {
		JSONArray buttons = menu.getJSONArray("button");
		List<Button> buttonList = new ArrayList<Button>(buttons.size());
		for (int i = 0; i < buttons.size(); i++) {
			buttonList.add(JSON.parseObject(buttons.getString(i), Button.class,
					buttonProcess));
		}
		return buttonList;
	}
}
