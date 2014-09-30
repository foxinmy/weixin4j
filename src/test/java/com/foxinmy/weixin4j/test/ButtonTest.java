package com.foxinmy.weixin4j.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Button;
import com.foxinmy.weixin4j.type.ButtonType;

/**
 * 自定义菜单测试
 * @className ButtonTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class ButtonTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		weixinProxy = new WeixinProxy();
	}

	@Test
	public void create() {
		List<Button> btnList = new ArrayList<Button>();

		Button b = new Button("test");
		b.setType(ButtonType.click);
		b.setKey("123");
		btnList.add(b);
		
		b = new Button("news","http://xxx");
		btnList.add(b);

		try {
			weixinProxy.createMenu(btnList);
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void get() {
		try {
			List<Button> btnList = weixinProxy.getMenu();
			for (Button btn : btnList) {
				System.out.println(btn.toString());
			}
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void delete() {
		try {
			weixinProxy.deleteMenu();
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
}
