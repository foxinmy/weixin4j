package com.foxinmy.weixin4j.mp.test.msg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.mp.api.MassApi;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.mp.test.TokenTest;
import com.foxinmy.weixin4j.msg.event.MassEventMessage;
import com.foxinmy.weixin4j.msg.model.Image;
import com.foxinmy.weixin4j.msg.model.MpArticle;
import com.foxinmy.weixin4j.msg.model.Text;
import com.foxinmy.weixin4j.msg.model.Video;
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
public class MassMsgTest extends TokenTest {
	private MassApi massApi;
	private MediaApi mediaApi;

	@Before
	public void init() {
		this.massApi = new MassApi(tokenHolder);
		this.mediaApi = new MediaApi(tokenHolder);
	}

	@Test
	public void uploadArticle() throws IOException, WeixinException {
		List<MpArticle> articles = new ArrayList<MpArticle>();
		String thumbMediaId = mediaApi.uploadMedia(new File("/tmp/test.jpg"),
				MediaType.image);
		articles.add(new MpArticle(thumbMediaId, "title", "content"));
		massApi.uploadArticle(articles);
	}

	@Test
	public void uploadVideo() throws WeixinException {
		Video video = new Video("mediaId", "title", "desc");
		String massId = massApi.uploadVideo(video);
		Assert.assertTrue(massId != null);
	}

	@Test
	public void massByGroupId() throws WeixinException {
		String msgId = massApi.massByGroupId(new Image("mediaId"), 0);
		Assert.assertTrue(msgId != null);
	}

	@Test
	public void massByOpenIds() throws WeixinException {
		String msgId = massApi.massByOpenIds(new Text("HI"), "oyFLst1bqtuTcxK-ojF8hOGtLQao");
		Assert.assertTrue(msgId != null);
	}

	@Test
	public void massArticleByGroup() throws IOException, WeixinException {
		List<MpArticle> articles = new ArrayList<MpArticle>();
		String thumbMediaId = mediaApi.uploadMedia(new File("/tmp/test.jpg"),
				MediaType.image);
		articles.add(new MpArticle(thumbMediaId, "title", "content"));
		String massId = massApi.massArticleByGroupId(articles, 0);
		Assert.assertTrue(massId != null);
	}

	@Test
	public void massArticleByOpenIds() throws IOException, WeixinException {
		List<MpArticle> articles = new ArrayList<MpArticle>();
		String thumbMediaId = mediaApi.uploadMedia(new File("/tmp/test.jpg"),
				MediaType.image);
		articles.add(new MpArticle(thumbMediaId, "title", "content"));
		String massId = massApi.massArticleByOpenIds(articles,
				"owGBft_vbBbOaQOmpEUE4xDLeRSU");
		Assert.assertTrue(massId != null);
	}

	@Test
	public void deleteMass() throws WeixinException {
		JsonResult result = massApi.deleteMassNews("34182");
		Assert.assertEquals(0, result.getCode());
	}

	@Test
	public void previewMass() throws WeixinException {
		JsonResult result = massApi.previewMassNews("oyFLst1bqtuTcxK-ojF8hOGtLQao", new Text("test"));
		Assert.assertEquals("0", result.getCode());
	}
	
	@Test
	public void getMassNews() throws WeixinException {
		String status = massApi.getMassNews("82358");
		System.out.println(status);
		System.out.println(MassEventMessage.getStatusDesc(status));
		Assert.assertNotNull(status);
	}
}
