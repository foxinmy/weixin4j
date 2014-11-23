package com.foxinmy.weixin4j.mp.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 菜单相关API
 * 
 * @className MenuApi
 * @author jy.hu
 * @date 2014年9月25日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.model.Button
 */
public class MenuApi extends BaseApi {

	private final TokenHolder tokenHolder;

	public MenuApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 自定义菜单
	 * 
	 * @param btnList
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%9B%E5%BB%BA%E6%8E%A5%E5%8F%A3">创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public JsonResult createMenu(List<Button> btnList) throws WeixinException {
		String menu_create_uri = getRequestUri("menu_create_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("button", btnList);
		Response response = request.post(
				String.format(menu_create_uri, token.getAccessToken()),
				obj.toJSONString());

		return response.getAsJsonResult();
	}

	/**
	 * 查询菜单
	 * 
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E6%9F%A5%E8%AF%A2%E6%8E%A5%E5%8F%A3">查询菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public List<Button> getMenu() throws WeixinException {
		String menu_get_uri = getRequestUri("menu_get_uri");
		Token token = tokenHolder.getToken();
		Response response = request.get(String.format(menu_get_uri,
				token.getAccessToken()));

		String text = response.getAsJson().getJSONObject("menu")
				.getString("button");
		return JSON.parseArray(text, Button.class);
	}

	/**
	 * 删除菜单
	 * 
	 * @throws WeixinException
	 * @see <a
	 *      href="http://mp.weixin.qq.com/wiki/index.php?title=%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E5%88%A0%E9%99%A4%E6%8E%A5%E5%8F%A3">删除菜单</a>
	 * @return 处理结果
	 */
	public JsonResult deleteMenu() throws WeixinException {
		String menu_delete_uri = getRequestUri("menu_delete_uri");
		Token token = tokenHolder.getToken();
		Response response = request.get(String.format(menu_delete_uri,
				token.getAccessToken()));

		return response.getAsJsonResult();
	}
}
