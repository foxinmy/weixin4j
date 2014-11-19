package com.foxinmy.weixin4j.qy.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.http.Response;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.Department;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * 部门API
 * 
 * @className DepartApi
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see com.foxinmy.weixin4j.qy.model.Department
 * @see <a
 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8">管理部门说明</a>
 */
public class DepartApi extends BaseApi {
	private final TokenHolder tokenHolder;

	public DepartApi(TokenHolder tokenHolder) {
		this.tokenHolder = tokenHolder;
	}

	/**
	 * 创建部门(根部门的parentid为1)
	 * 
	 * @param depart
	 *            部门对象
	 * @see com.foxinmy.weixin4j.qy.model.Department
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E5.88.9B.E5.BB.BA.E9.83.A8.E9.97.A8">创建部门说明</a>
	 * @return 部门ID
	 * @throws WeixinException
	 */
	public int createDepart(Department depart) throws WeixinException {
		String department_create_uri = getRequestUri("department_create_uri");
		JSONObject obj = (JSONObject) JSON.toJSON(depart);
		obj.remove("id");
		Token token = tokenHolder.getToken();
		Response response = request.post(
				String.format(department_create_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJson().getIntValue("id");
	}

	/**
	 * 更新部门(如果非必须的字段未指定 则不更新该字段之前的设置值)
	 * 
	 * @param depart
	 *            部门对象
	 * @see com.foxinmy.weixin4j.qy.model.Department
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E6.9B.B4.E6.96.B0.E9.83.A8.E9.97.A8">更新部门说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult updateDepart(Department depart) throws WeixinException {
		String department_update_uri = getRequestUri("department_update_uri");
		JSONObject obj = (JSONObject) JSON.toJSON(depart);
		Token token = tokenHolder.getToken();
		Response response = request.post(
				String.format(department_update_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJsonResult();
	}

	/**
	 * 查询部门列表(以部门的order字段从小到大排列)
	 * 
	 * @see com.foxinmy.weixin4j.qy.model.Department
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E8.8E.B7.E5.8F.96.E9.83.A8.E9.97.A8.E5.88.97.E8.A1.A8">获取部门列表</a>
	 * @return 部门列表
	 * @throws WeixinException
	 */
	public List<Department> listDepart() throws WeixinException {
		String department_list_uri = getRequestUri("department_list_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(String.format(department_list_uri,
				token.getAccessToken()));
		return JSON.parseArray(response.getAsJson().getString("department"),
				Department.class);
	}

	/**
	 * 删除部门(不能删除根部门；不能删除含有子部门、成员的部门)
	 * 
	 * @param departId
	 *            部门ID
	 * @see <a
	 *      href="http://qydev.weixin.qq.com/wiki/index.php?title=%E7%AE%A1%E7%90%86%E9%83%A8%E9%97%A8#.E5.88.A0.E9.99.A4.E9.83.A8.E9.97.A8">删除部门说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public JsonResult deleteDepart(int departId) throws WeixinException {
		String department_delete_uri = getRequestUri("department_delete_uri");
		Token token = tokenHolder.getToken();
		Response response = request.post(String.format(department_delete_uri,
				token.getAccessToken(), departId));
		return response.getAsJsonResult();
	}
}
