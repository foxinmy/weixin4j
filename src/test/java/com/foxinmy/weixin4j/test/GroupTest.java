package com.foxinmy.weixin4j.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Group;

/**
 * 用户分组测试
 * @className GroupTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class GroupTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		weixinProxy = new WeixinProxy();
	}

	@Test
	public void create() {
		try {
			System.out.println(weixinProxy.createGroup("test"));
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void get() {
		try {
			List<Group> groups = weixinProxy.getGroups();
			for (Group group : groups) {
				System.out.println(group);
			}
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void getid() {
		try {
			System.out.println(weixinProxy.getGroupByOpenId("owGBft_vbBbOaQOmpEUE4xDLeRSU"));
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void modify() {
		try {
			weixinProxy.modifyGroup(100, "test1");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
	@Test
	public void move() {
		try {
			weixinProxy.moveGroup("owGBft_vbBbOaQOmpEUE4xDLeRSU",100);
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
}
