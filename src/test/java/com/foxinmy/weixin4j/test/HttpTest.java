package com.foxinmy.weixin4j.test;

import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.HttpRequest;

public class HttpTest {
	private static HttpRequest request;
	static {
		request = new HttpRequest();
	}
	
	@Test
	public void test(){
		try {
			System.out.println(request.get("http://www.baidu.com"));
		} catch (WeixinException e) {
			e.printStackTrace();
		}
	}
}
