package com.foxinmy.weixin4j.qy.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.qy.api.DepartApi;
import com.foxinmy.weixin4j.qy.model.Department;

/**
 * 部门API测试
 * 
 * @className DepartTest
 * @author jy
 * @date 2014年11月18日
 * @since JDK 1.7
 * @see
 */
public class DepartTest extends TokenTest {
	public DepartApi departApi;

	@Before
	public void init() {
		this.departApi = new DepartApi(tokenHolder);
	}

	@Test
	public void create() throws WeixinException {
		Department depart = new Department("苦逼组");
		int id = departApi.createDepart(depart);
		Assert.assertTrue(id > 0);
	}

	@Test
	public void update() throws WeixinException {
		Department depart = new Department("苦逼组111");
		depart.setId(2);
		JsonResult result = departApi.updateDepart(depart);
		Assert.assertEquals("updated", result.getDesc());
	}

	@Test
	public void list() throws WeixinException {
		List<Department> list = departApi.listDepart(1);
		Assert.assertFalse(list.isEmpty());
		System.out.println(list);
	}
	
	@Test
	public void delete() throws WeixinException {
		JsonResult result = departApi.deleteDepart(2);
		Assert.assertEquals("deleted", result.getDesc());
	}
}
