package com.foxinmy.weixin4j.mp.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.mp.api.MenuApi;
import com.foxinmy.weixin4j.mp.model.Button;
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

		Button b = new Button("click");
		b.setType(ButtonType.click);
		b.setKey("click");
		btnList.add(b);

		b = new Button("qq", "http://www.qq.com");
		btnList.add(b);

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
