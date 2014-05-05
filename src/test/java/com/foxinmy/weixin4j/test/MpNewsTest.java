package com.foxinmy.weixin4j.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.MpArticle;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 群发消息
 * 
 * @className MpNewsTest
 * @author jy
 * @date 2014年4月27日
 * @since JDK 1.7
 * @see
 */
public class MpNewsTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		this.weixinProxy = new WeixinProxy();
	}
	
	@Test
	public void massByGroup(){
		List<MpArticle> articles = new ArrayList<MpArticle>();
		try {
			String thumbMediaId = weixinProxy.uploadMedia(new File("/tmp/test.jpg"), MediaType.image);
			articles.add(new MpArticle(thumbMediaId, "title", "content"));
			weixinProxy.massNewsByGroup(articles, "0");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
	
	@Test
	public void massByOpenIds(){
		List<MpArticle> articles = new ArrayList<MpArticle>();
		try {
			String thumbMediaId = weixinProxy.uploadMedia(new File("/tmp/test.jpg"), MediaType.image);
			articles.add(new MpArticle(thumbMediaId, "title", "content"));
			weixinProxy.massNewsByOpenIds(articles, "owGBft_vbBbOaQOmpEUE4xDLeRSU");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
	
	@Test
	public void deleteMass(){
		try {
			weixinProxy.deleteMassNews("34182");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
}
