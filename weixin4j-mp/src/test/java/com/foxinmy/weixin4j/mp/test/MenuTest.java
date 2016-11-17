package com.foxinmy.weixin4j.mp.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.mp.api.MenuApi;
import com.foxinmy.weixin4j.mp.model.Menu;
import com.foxinmy.weixin4j.mp.model.MenuMatchRule;
import com.foxinmy.weixin4j.mp.type.ClientPlatformType;
import com.foxinmy.weixin4j.type.ButtonType;

/**
 * 自定义菜单测试
 * 
 * @className MenuTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class MenuTest extends TokenTest {

	private MenuApi menuApi;
	private List<Button> buttons;

	@Before
	public void init() {
		menuApi = new MenuApi(tokenManager);
	}

	@Test
	public void create() throws WeixinException {
		buttons = new ArrayList<Button>();
		String domain = "http://wx.jdxg.doubimeizhi.com";
		buttons.add(new Button("立即下单", domain, ButtonType.view));

		//buttons.add(new Button("个人中心", domain + "/user", ButtonType.view));

		Button button = new Button("小哥介绍", domain, ButtonType.view);
		button.pushSub(new Button("小哥介绍", "http://x.eqxiu.com/s/89oy462U",
				ButtonType.view));
		button.pushSub(new Button("小哥官网", "http://www.jdxiaoge.com",
				ButtonType.view));
		button.pushSub(new Button("兴趣部落",
				"http://buluo.qq.com/p/barindex.html?from=share&bid=282651",
				ButtonType.view));
		button.pushSub(new Button("服务流程", "FLOW", ButtonType.click));
		button.pushSub(new Button("在线客服", "KF", ButtonType.click));
		//buttons.add(button);

		ApiResult result = menuApi.createMenu(buttons);
		Assert.assertEquals("0", result.getReturnCode());
	}

	@Test
	public void create1() throws WeixinException {
		buttons = new ArrayList<Button>();

		Button b1 = new Button("我要订餐", "ORDERING", ButtonType.click);
		buttons.add(b1);
		Button b2 = new Button("查询订单", "http://www.lendocean.com/order/list",
				ButtonType.view);
		buttons.add(b2);
		Button b3 = new Button("最新资讯", "NEWS", ButtonType.click);
		buttons.add(b3);
		ApiResult result = menuApi.createMenu(buttons);
		Assert.assertEquals("0", result.getReturnCode());
	}

	@Test
	public void get() throws WeixinException {
		buttons = menuApi.getMenu();
		for (Button btn : buttons) {
			System.out.println(btn);
		}
		Assert.assertEquals(3, buttons.size());
		// Button [name=我的门店, type=view,
		// content=http://dianzhang.canyi.net/setting/index, subs=[]]
		// Button [name=每日签到, type=click, content=CHECKIN, subs=[]]
		// Button [name=今日订单, type=null, content=null, subs=[Button [name=今日订单,
		// type=view, content=http://dianzhang.canyi.net/order/index, subs=[]],
		// Button [name=营业统计, type=view,
		// content=http://dianzhang.canyi.net/stats/index, subs=[]]]]

	}

	@Test
	public void delete() throws WeixinException {
		ApiResult result = menuApi.deleteMenu();
		Assert.assertEquals("0", result.getReturnCode());
	}

	@Test
	public void testCustom() throws WeixinException {
		buttons = new ArrayList<Button>();
		buttons.add(new Button("only for iphone", "iphone", ButtonType.click));
		MenuMatchRule matchRule = new MenuMatchRule();
		matchRule.platform(ClientPlatformType.IOS);
		Assert.assertNotNull(menuApi.createCustomMenu(buttons, matchRule));
	}

	@Test
	public void testGetAllMenus() throws WeixinException {
		List<Menu> menus = menuApi.getAllMenu();
		System.err.println(menus);
	}

	@Test
	public void testMatchMenu() throws WeixinException {
		List<Button> buttons = menuApi.matchCustomMenu("paihuaing");
		System.err.println(buttons);
	}
}
