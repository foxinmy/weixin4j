package com.foxinmy.weixin4j.qy.api;

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
public class MenuApi extends QyApi {

	private final TokenHolder tokenHolder;

	public MenuApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 自定义菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式)
	 * 
	 * @param btnList
	 * @throws WeixinException
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%88%9B%E5%BB%BA%E5%BA%94%E7%94%A8%E8%8F%9C%E5%8D%95">创建自定义菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public JsonResult createMenu(List<Button> btnList, int agentid)
			throws WeixinException {
		String menu_create_uri = getRequestUri("menu_create_uri");
		Token token = tokenHolder.getToken();
		JSONObject obj = new JSONObject();
		obj.put("button", btnList);
		Response response = request
				.post(String.format(menu_create_uri, token.getAccessToken(),
						agentid), obj.toJSONString());

		return response.getAsJsonResult();
	}

	/**
	 * 查询菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式。)
	 * 
	 * @return 菜单集合
	 * @throws WeixinException
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96%E8%8F%9C%E5%8D%95%E5%88%97%E8%A1%A8">查询菜单</a>
	 * @see com.foxinmy.weixin4j.model.Button
	 */
	public List<Button> getMenu(int agentid) throws WeixinException {
		String menu_get_uri = getRequestUri("menu_get_uri");
		Token token = tokenHolder.getToken();
		Response response = request.get(String.format(menu_get_uri,
				token.getAccessToken(), agentid));

		String text = response.getAsJson().getJSONObject("menu")
				.getString("button");
		return JSON.parseArray(text, Button.class);
	}

	/**
	 * 删除菜单(管理员须拥有应用的管理权限 并且应用必须设置在回调模式)
	 * 
	 * @throws WeixinException
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%88%A0%E9%99%A4%E8%8F%9C%E5%8D%95">删除菜单</a>
	 * @return 处理结果
	 */
	public JsonResult deleteMenu(int agentid) throws WeixinException {
		String menu_delete_uri = getRequestUri("menu_delete_uri");
		Token token = tokenHolder.getToken();
		Response response = request.get(String.format(menu_delete_uri,
				token.getAccessToken(), agentid));

		return response.getAsJsonResult();
	}
}
