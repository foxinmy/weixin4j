package com.foxinmy.weixin4j.mp.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.JsonResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.mp.api.MenuApi;
import com.foxinmy.weixin4j.type.ButtonType;

/**
 * 自定义菜单测试
 * 
 * @className MenuTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class MenuTest extends TokenTest {

	private MenuApi menuApi;
	private List<Button> btnList;

	@Before
	public void init() {
		menuApi = new MenuApi(tokenHolder);
	}

	@Test
	public void create() throws WeixinException {
		btnList = new ArrayList<Button>();

		Button b1 = new Button("会员中心", "", ButtonType.click);
		b1.pushSub(new Button("我的信息", "U:INFO", ButtonType.click));
		b1.pushSub(new Button("修改信息", "U:UP:INFO", ButtonType.click));
		btnList.add(b1);

		Button b2 = new Button("最新兼职", "PART:NEWEST", ButtonType.click);
		btnList.add(b2);

		Button b3 = new Button("功能", "", ButtonType.click);
		b3.pushSub(new Button("附近兼职", "PART:NEAR", ButtonType.click));
		b3.pushSub(new Button("搜索兼职", "PART:SO", ButtonType.click));
		b3.pushSub(new Button("公交查询", "BUS:SO", ButtonType.click));
		btnList.add(b3);

		JsonResult result = menuApi.createMenu(btnList);
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void create1() throws WeixinException {
		btnList = new ArrayList<Button>();

		Button b1 = new Button("我要订餐", "ORDERING", ButtonType.click);
		btnList.add(b1);
		Button b2 = new Button("查询订单", "http://www.lendocean.com/order/list",
				ButtonType.view);
		btnList.add(b2);
		Button b3 = new Button("最新资讯", "NEWS", ButtonType.click);
		btnList.add(b3);
		JsonResult result = menuApi.createMenu(btnList);
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void get() throws WeixinException {
		btnList = menuApi.getMenu();
		for (Button btn : btnList) {
			System.out.println(btn);
		}
		Assert.assertEquals(3, btnList.size());
	}

	@Test
	public void delete() throws WeixinException {
		JsonResult result = menuApi.deleteMenu();
		Assert.assertEquals(0, result.getCode());
	}
}
