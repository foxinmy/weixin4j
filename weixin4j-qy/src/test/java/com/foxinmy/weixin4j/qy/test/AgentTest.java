package com.foxinmy.weixin4j.qy.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.qy.api.AgentApi;
import com.foxinmy.weixin4j.qy.model.AgentInfo;
import com.foxinmy.weixin4j.qy.model.AgentOverview;
import com.foxinmy.weixin4j.qy.model.AgentSetter;
import com.foxinmy.weixin4j.qy.type.ReportLocationType;

/**
 * 应用API测试
 * 
 * @className AgentTest
 * @author jy
 * @date 2015年03月17日
 * @since JDK 1.7
 * @see
 */
public class AgentTest extends TokenTest {
	public AgentApi agentApi;

	@Before
	public void init() {
		this.agentApi = new AgentApi(tokenHolder);
	}

	@Test
	public void get() throws WeixinException {
		AgentInfo agent = agentApi.getAgent(0);
		Assert.assertTrue(agent != null);
		System.err.println(agent);
	}

	@Test
	public void set() throws WeixinException {
		AgentSetter agentSet = new AgentSetter(1);
		agentSet.setDescription("test");
		agentSet.setRedirectDomain("test.com");
		agentSet.setReportLocationType(ReportLocationType.DIALOG);
		JsonResult result = agentApi.setAgent(agentSet);
		Assert.assertTrue(result.getCode() == 0);
	}
	
	@Test
	public void list() throws WeixinException {
		List<AgentOverview> list = agentApi.listAgentOverview();
		System.err.println(list);
	}
}
