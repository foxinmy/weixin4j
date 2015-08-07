package com.foxinmy.weixin4j.qy.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.qy.api.PartyApi;
import com.foxinmy.weixin4j.qy.model.Party;

/**
 * 部门API测试
 * 
 * @className PartyTest
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see
 */
public class PartyTest extends TokenTest {
	public PartyApi partyApi;

	@Before
	public void init() {
		this.partyApi = new PartyApi(tokenHolder);
	}

	@Test
	public void create() throws WeixinException {
		Party Party = new Party(1, "苦逼组", 1);
		int id = partyApi.createParty(Party);
		Assert.assertTrue(id > 0);
	}

	@Test
	public void update() throws WeixinException {
		Party Party = new Party(2, "苦逼组111", 1);
		JsonResult result = partyApi.updateParty(Party);
		Assert.assertEquals("updated", result.getDesc());
	}

	@Test
	public void list() throws WeixinException {
		List<Party> list = partyApi.listParty(0);
		Assert.assertFalse(list.isEmpty());
		System.out.println(list);
	}

	@Test
	public void delete() throws WeixinException {
		JsonResult result = partyApi.deleteParty(2);
		Assert.assertEquals("deleted", result.getDesc());
	}
}
