package com.foxinmy.weixin4j.qy.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.Party;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 部门API
 *
 * @className PartyApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月18日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.qy.model.Party
 * @see <a href="https://work.weixin.qq.com/api/doc#10076">管理部门说明</a>
 */
public class PartyApi extends QyApi {
	private final TokenManager tokenManager;

	public PartyApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 创建部门(根部门的parentid为1)
	 *
	 * @param party
	 *            部门对象
	 * @see com.foxinmy.weixin4j.qy.model.Party
	 * @see <a href="https://work.weixin.qq.com/api/doc#10076">创建部门说明</a>
	 * @return 部门ID
	 * @throws WeixinException
	 */
	public int createParty(Party party) throws WeixinException {
		String department_create_uri = getRequestUri("department_create_uri");
		JSONObject obj = (JSONObject) JSON.toJSON(party);
		if (party.getParentId() < 1) {
			obj.remove("parentid");
		}
		if (party.getId() < 1) {
			obj.remove("id");
		}
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(department_create_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsJson().getIntValue("id");
	}

	/**
	 * 更新部门(如果非必须的字段未指定 则不更新该字段之前的设置值)
	 *
	 * @param party
	 *            部门对象
	 * @see com.foxinmy.weixin4j.qy.model.Party
	 * @see <a href="https://work.weixin.qq.com/api/doc#10077">更新部门说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult updateParty(Party party) throws WeixinException {
		if (party.getId() < 1) {
			throw new WeixinException("department id must gt 1");
		}
		String department_update_uri = getRequestUri("department_update_uri");
		JSONObject obj = (JSONObject) JSON.toJSON(party);
		if (party.getParentId() < 1) {
			obj.remove("parentid");
		}
		if (party.getOrder() < 0) {
			obj.remove("order");
		}
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(department_update_uri, token.getAccessToken()),
				obj.toJSONString());
		return response.getAsResult();
	}

	/**
	 * 查询部门列表(以部门的order字段从小到大排列)
	 *
	 * @param partId
	 *            部门ID。获取指定部门ID下的子部门 传入0表示获取全部子部门
	 * @see com.foxinmy.weixin4j.qy.model.Party
	 * @see <a href="https://work.weixin.qq.com/api/doc#10093">获取部门列表</a>
	 * @return 部门列表
	 * @throws WeixinException
	 */
	public List<Party> listParty(int partId) throws WeixinException {
		String department_list_uri = getRequestUri("department_list_uri");
		if (partId > 0) {
			department_list_uri += String.format("&id=%d", partId);
		}
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				department_list_uri, token.getAccessToken()));
		return JSON.parseArray(response.getAsJson().getString("department"),
				Party.class);
	}

	/**
	 * 删除部门(不能删除根部门；不能删除含有子部门、成员的部门)
	 *
	 * @param partId
	 *            部门ID
	 * @see <a href="https://work.weixin.qq.com/api/doc#10079">删除部门说明</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult deleteParty(int partId) throws WeixinException {
		String department_delete_uri = getRequestUri("department_delete_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				department_delete_uri, token.getAccessToken(), partId));
		return response.getAsResult();
	}
}
