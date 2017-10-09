package com.foxinmy.weixin4j.qy.api;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.qy.model.AgentInfo;
import com.foxinmy.weixin4j.qy.model.AgentOverview;
import com.foxinmy.weixin4j.qy.model.AgentSetter;
import com.foxinmy.weixin4j.qy.model.User;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 管理应用接口
 * 
 * @className AgentApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月16日
 * @since JDK 1.6
 * @see <a href="https://work.weixin.qq.com/api/doc#10025">管理应用接口说明</a>
 */
public class AgentApi extends QyApi {
	private final TokenManager tokenManager;

	public AgentApi(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	/**
	 * 获取企业号某个应用的基本信息，包括头像、昵称、帐号类型、认证类型、可见范围等信息
	 * 
	 * @param agentid
	 *            授权方应用id
	 * @return 应用信息
	 * @see com.foxinmy.weixin4j.qy.model.AgentInfo
	 * @see <a href="https://work.weixin.qq.com/api/doc#10087">企业号应用的信息</a>
	 * @throws WeixinException
	 */
	public AgentInfo getAgent(int agentid) throws WeixinException {
		String agent_get_uri = getRequestUri("agent_get_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				agent_get_uri, token.getAccessToken(), agentid));
		JSONObject jsonObj = response.getAsJson();
		AgentInfo agent = JSON.toJavaObject(jsonObj, AgentInfo.class);
		agent.setAllowUsers(JSON.parseArray(
				jsonObj.getJSONObject("allow_userinfos").getString("user"),
				User.class));
		agent.setAllowPartys(JSON.parseArray(
				jsonObj.getJSONObject("allow_partys").getString("partyid"),
				Integer.class));
		agent.setAllowTags(JSON.parseArray(jsonObj.getJSONObject("allow_tags")
				.getString("tagid"), Integer.class));
		return agent;
	}

	/**
	 * 设置企业应用的选项设置信息，如：地理位置上报等
	 * 
	 * @param agentSet
	 *            设置信息
	 * @see com.foxinmy.weixin4j.qy.model.AgentSetter
	 * @see <a href="https://work.weixin.qq.com/api/doc#10088">设置企业号信息</a>
	 * @return 处理结果
	 * @throws WeixinException
	 */
	public ApiResult setAgent(AgentSetter agentSet) throws WeixinException {
		String agent_set_uri = getRequestUri("agent_set_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.post(
				String.format(agent_set_uri, token.getAccessToken()),
				JSON.toJSONString(agentSet, typeFilter));
		return response.getAsResult();
	}

	public final static ValueFilter typeFilter;
	static {
		typeFilter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (value instanceof Boolean) {
					return ((Boolean) value) ? 1 : 0;
				}
				if (value instanceof Enum) {
					return ((Enum<?>) value).ordinal();
				}
				return value;
			}
		};
	}

	/**
	 * 获取应用概况列表
	 * 
	 * @see com.foxinmy.weixin4j.qy.model.AgentOverview
	 * @see <a href="https://work.weixin.qq.com/api/doc#11214">获取应用概况</a>
	 * @return 应用概况列表
	 * @throws WeixinException
	 */
	public List<AgentOverview> listAgentOverview() throws WeixinException {
		String agent_list_uri = getRequestUri("agent_list_uri");
		Token token = tokenManager.getCache();
		WeixinResponse response = weixinExecutor.get(String.format(
				agent_list_uri, token.getAccessToken()));

		return JSON.parseArray(response.getAsJson().getString("agentlist"),
				AgentOverview.class);
	}
}
