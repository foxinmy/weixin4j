package com.foxinmy.weixin4j.mp.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
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

		Button b1 = new Button("我要订餐", "ORDERING", ButtonType.click);
		btnList.add(b1);

		Button b2 = new Button("我", "", ButtonType.click);
		b2.pushSub(new Button("个人中心", "MINE", ButtonType.click));
		b2.pushSub(new Button("会员中心", "MEMRBER", ButtonType.click));
		b2.pushSub(new Button("我的积分", "SCORE", ButtonType.click));
		b2.pushSub(new Button("我的优惠券", "COUPON", ButtonType.click));
		b2.pushSub(new Button("我的订单", "MYORDER", ButtonType.click));
		btnList.add(b2);

		Button b3 = new Button("商家功能", "", ButtonType.click);
		b3.pushSub(new Button("商家主页", "SHOPLIST", ButtonType.click));
		b3.pushSub(new Button("大转盘", "WHEEL", ButtonType.click));
		b3.pushSub(new Button("店铺区域", "SHOPAREA", ButtonType.click));
		b3.pushSub(new Button("店铺口味", "SHOPTASTE", ButtonType.click));
		btnList.add(b3);

		JsonResult result = menuApi.createMenu(btnList);
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void create1() throws WeixinException {
		btnList = new ArrayList<Button>();

		Button b1 = new Button("我要订餐", "ORDERING", ButtonType.click);
		btnList.add(b1);
		Button b2 = new Button("查询订单", "http://802.canyi.net/order/list", ButtonType.view);
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
		Assert.assertEquals(1, btnList.size());
	}

	@Test
	public void delete() throws WeixinException {
		JsonResult result = menuApi.deleteMenu();
		Assert.assertEquals(0, result.getCode());
	}
}
