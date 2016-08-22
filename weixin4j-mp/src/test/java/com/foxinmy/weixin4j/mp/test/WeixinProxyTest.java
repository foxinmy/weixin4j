package com.foxinmy.weixin4j.mp.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.mp.model.Menu;

/**
 * 微信接口测试
 * 
 * @className WeixinProxyTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年8月18日
 * @since JDK 1.6
 * @see
 */
public class WeixinProxyTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		this.weixinProxy = new WeixinProxy();
		// Weixin4jSettings<WeixinAccount> settings = new
		// Weixin4jSettings<WeixinAccount>(RediscacheStorager);
		// this.weixinProxy= new WeixinProxy(settings);
	}
	
	@Test
	public void test() throws WeixinException{
		List<Menu> buttons = weixinProxy.getAllMenu();
		System.err.println(buttons);
	}
}
