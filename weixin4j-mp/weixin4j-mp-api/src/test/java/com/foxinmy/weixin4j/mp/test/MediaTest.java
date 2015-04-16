package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.JsonResult;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.mp.model.MediaCounter;
import com.foxinmy.weixin4j.mp.model.MediaRecord;
import com.foxinmy.weixin4j.msg.model.MpArticle;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 媒体上传下载测试
 * 
 * @className MediaTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 * @see
 */
public class MediaTest extends TokenTest {

	private MediaApi mediaApi;

	@Before
	public void init() {
		mediaApi = new MediaApi(tokenHolder);
	}

	@Test
	public void upload1() throws IOException, WeixinException {
		File file = new File("/Users/jy/Downloads/test.jpg");
		String mediaId = mediaApi.uploadMedia(file, MediaType.image, false);
		// Sy1KOLsi4ri3kB3TYUuculVelcW2I7W6BrfGwkGvSW8beTCAarxuGQLjuNJChJr8
		Assert.assertNotNull(mediaId);
		System.err.println(mediaId);
	}

	@Test
	public void download1() throws WeixinException, IOException {
		File file = mediaApi
				.downloadMedia(
						"Sy1KOLsi4ri3kB3TYUuculVelcW2I7W6BrfGwkGvSW8beTCAarxuGQLjuNJChJr8",
						MediaType.image, false);
		Assert.assertTrue(file.exists());
	}

	@Test
	public void upload2() throws IOException, WeixinException {
		File file = new File("/Users/jy/Downloads/test.jpg");
		String mediaId = mediaApi.uploadMedia(file, MediaType.image, true);
		// 8790403529
		Assert.assertNotNull(mediaId);
		System.err.println(mediaId);
	}

	@Test
	public void uploadMaterialVideo() throws IOException, WeixinException {
		File file = new File("/Users/jy/Downloads/test.jpg");
		String mediaId = mediaApi.uploadMaterialVideo(file, "title",
				"introduction");
		// Sy1KOLsi4ri3kB3TYUuculVelcW2I7W6BrfGwkGvSW8beTCAarxuGQLjuNJChJr8
		Assert.assertNotNull(mediaId);
		System.err.println(mediaId);
	}

	@Test
	public void uploadMaterialArticle() throws WeixinException {
		List<MpArticle> articles = new ArrayList<MpArticle>();
		articles.add(new MpArticle("8790403529", "title", "content"));
		String mediaId = mediaApi.uploadMaterialArticle(articles);
		// 17385064953
		Assert.assertNotNull(mediaId);
		System.err.println(mediaId);
	}

	@Test
	public void download2() throws WeixinException, IOException {
		File file = mediaApi.downloadMedia("8790403529", MediaType.image, true);
		Assert.assertTrue(file.exists());
	}

	@Test
	public void downloadArticle() throws WeixinException {
		List<MpArticle> articles = mediaApi.downloadArticle("17385064953");
		Assert.assertTrue(articles != null && !articles.isEmpty());
		System.err.println(articles);
	}

	@Test
	public void deleteMaterialMedia() throws WeixinException {
		JsonResult result = mediaApi.deleteMaterialMedia("17385064953");
		System.err.println(result);
	}

	@Test
	public void updateMaterialArticle() throws WeixinException {
		MpArticle mpArticle = new MpArticle("8790403529", "title", "content");
		mpArticle.setAuthor("author_update");
		mpArticle.setDigest("digest_update");
		mpArticle.setShowCoverPic(false);
		mpArticle.setSourceUrl("http://www.baidu.com");
		List<MpArticle> articles = new ArrayList<MpArticle>();
		articles.add(mpArticle);
		JsonResult result = mediaApi.updateMaterialArticle("17385064953", 0,
				articles);
		System.err.println(result);
		// 17385065153
	}

	@Test
	public void countMaterialMedia() throws WeixinException {
		MediaCounter counter = mediaApi.countMaterialMedia();
		System.err.println(counter);
	}

	@Test
	public void listMaterialMedia() throws WeixinException {
		MediaRecord mediaRecord = mediaApi.listMaterialMedia(MediaType.news, 0,
				20);
		System.err.println(mediaRecord);
	}
}
