package com.foxinmy.weixin4j.qy.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.qy.api.MenuApi;
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
	private List<Button> btnList;

	@Before
	public void init() {
		menuApi = new MenuApi(tokenManager);
	}

	@Test
	public void create() throws WeixinException {
		btnList = new ArrayList<Button>();

		Button b = new Button("click", "name", ButtonType.click);
		btnList.add(b);

		b = new Button("qq", "http://www.qq.com", ButtonType.view);
		btnList.add(b);
		b = new Button("photo", "photo", ButtonType.pic_photo_or_album);
		btnList.add(b);
		ApiResult result = menuApi.createMenu(1, btnList);
		Assert.assertEquals("0", result.getReturnCode());
	}

	@Test
	public void get() throws WeixinException {
		btnList = menuApi.getMenu(0);
		for (Button btn : btnList) {
			System.out.println(btn);
		}
		Assert.assertEquals(2, btnList.size());
	}

	@Test
	public void delete() throws WeixinException {
		ApiResult result = menuApi.deleteMenu(1);
		Assert.assertEquals("0", result.getReturnCode());
	}
}
