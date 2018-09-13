package com.foxinmy.weixin4j.wxa.api;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.wxa.WeixinAppFacade;

public class WxaApiTest {

	@Test
	public void testGetRequestUriStringObjectArrayDefault() {
		WxaApi wxaApi = new WxaApi() {
		};
		String uri = wxaApi.getRequestUri("sns_jscode2session", "myAppId", "mySecret", "myJsCode", "myGrantType");
		assertEquals("https://api.weixin.qq.com/sns/jscode2session?appid=myAppId&secret=mySecret&js_code=myJsCode&grant_type=myGrantType", uri);
	}

	@Test
	public void testGetRequestUriStringObjectArrayOverride() {
		Properties properties = new Properties();
		properties.setProperty("api_base_url", "https://api.example.com");
		WxaApi wxaApi = new WxaApi(properties) {
		};
		assertEquals(
			"https://api.example.com/sns/jscode2session?appid=myAppId&secret=mySecret&js_code=myJsCode&grant_type=myGrantType",
			wxaApi.getRequestUri("sns_jscode2session", "myAppId", "mySecret", "myJsCode", "myGrantType")
		);
	}

	@Test
	public void testFacadeWithProperties() {
		Properties properties = new Properties();
		properties.setProperty("api_base_url", "https://api.example.com");
		properties.setProperty("api_cgi_url", "{api_base_url}/cgi-bin2");

		WeixinAppFacade wxaFacade = new WeixinAppFacade(new WeixinAccount("myAppId", "mySecret"), new FileCacheStorager<Token>(), properties);

		assertEquals("https://api.example.com", wxaFacade.getLoginApi().getRequestUri("api_base_url"));

		assertEquals(
			"https://api.example.com/sns/jscode2session?appid=myAppId&secret=mySecret&js_code=myJsCode&grant_type=myGrantType",
			wxaFacade.getLoginApi().getRequestUri("sns_jscode2session", "myAppId", "mySecret", "myJsCode", "myGrantType")
		);

		assertEquals(
			"https://api.example.com/cgi-bin2/wxopen/template/library/list?access_token=myAccessToken",
			wxaFacade.getTemplateApi().getRequestUri("wxopen_template_library_list", "myAccessToken")
		);
	}

}
