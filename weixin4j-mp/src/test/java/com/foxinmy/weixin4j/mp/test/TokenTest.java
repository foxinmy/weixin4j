package com.foxinmy.weixin4j.mp.test;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.TokenHolder;

/**
 * token测试
 * 
 * @className TokenTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class TokenTest {

	protected TokenHolder tokenHolder;

	@Before
	public void setUp() {
		tokenHolder = new FileTokenHolder(new WeixinTokenCreator());
	}

	@Test
	public void test() throws WeixinException {
		Assert.assertNotNull(tokenHolder.getToken());
	}

	public static void main(String[] args) throws Exception {
		String wikiUrl = "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E";
		// wikiUrl =
		// "http://mp.weixin.qq.com/wiki/17/fa4e1434e57290788bde25603fa2fcbd.html";
		Document doc = Jsoup.parse(new URL(wikiUrl), 5000);
		Elements errors = doc.getElementsByTag("tr");
		String node = "<error><code>%s</code><text>%s</text></error>";
		StringBuilder xml = new StringBuilder();
		for (Element error : errors) {
			xml.append(String.format(node, error.child(0).text(), error
					.child(1).text()));
		}
		System.err.println(xml);
	}
}
