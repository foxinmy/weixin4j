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
public class MessageMassTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		this.weixinProxy = new WeixinProxy();
	}

	@Test
	public void uploadArticle() {
		List<MpArticle> articles = new ArrayList<MpArticle>();
		try {
			String thumbMediaId = weixinProxy.uploadMedia(new File(
					"/tmp/test.jpg"), MediaType.image);
			articles.add(new MpArticle(thumbMediaId, "title", "content"));
			weixinProxy.uploadArticle(articles);
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void uploadVideo() {
		try {
			weixinProxy.uploadVideo("mediaId", "title", "desc");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void massByGroup() {
		try {
			weixinProxy.massByGroup("123", MediaType.image, "groupId");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void massByOpenIds() {
		try {
			weixinProxy.massByOpenIds("123", MediaType.image, "openIds");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void massArticleByGroup() {
		List<MpArticle> articles = new ArrayList<MpArticle>();
		try {
			String thumbMediaId = weixinProxy.uploadMedia(new File(
					"/tmp/test.jpg"), MediaType.image);
			articles.add(new MpArticle(thumbMediaId, "title", "content"));
			weixinProxy.massArticleByGroup(articles, "0");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void massArticleByOpenIds() {
		List<MpArticle> articles = new ArrayList<MpArticle>();
		try {
			String thumbMediaId = weixinProxy.uploadMedia(new File(
					"/tmp/test.jpg"), MediaType.image);
			articles.add(new MpArticle(thumbMediaId, "title", "content"));
			weixinProxy.massArticleByOpenIds(articles,
					"owGBft_vbBbOaQOmpEUE4xDLeRSU");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void deleteMass() {
		try {
			weixinProxy.deleteMassNews("34182");
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}
}
