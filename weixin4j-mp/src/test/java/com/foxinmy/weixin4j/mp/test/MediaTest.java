package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.model.media.MediaCounter;
import com.foxinmy.weixin4j.model.media.MediaDownloadResult;
import com.foxinmy.weixin4j.model.media.MediaItem;
import com.foxinmy.weixin4j.model.media.MediaRecord;
import com.foxinmy.weixin4j.model.media.MediaUploadResult;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.tuple.MpVideo;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 媒体上传下载测试
 *
 * @className MediaTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 * @see
 */
public class MediaTest extends TokenTest {

	private MediaApi mediaApi;

	@Before
	public void init() {
		mediaApi = new MediaApi(tokenManager);
	}

	@Test
	public void upload1() throws IOException, WeixinException {
		File file = new File("/Users/jy/Downloads/weixin4j.png");
		MediaUploadResult mediaId = mediaApi.uploadMedia(false,
				new FileInputStream(file), file.getName());
		// PPHCwX-13V4_IdIchHIsI1VDcJyUB5ttJdnRArbAmWrNXSxX55fQ831N7B_R3l1c
		Assert.assertNotNull(mediaId);
		System.err.println(mediaId);
	}

	@Test
	public void download1() throws WeixinException, IOException {
		MediaDownloadResult content = mediaApi
				.downloadMedia(
						"PPHCwX-13V4_IdIchHIsI1VDcJyUB5ttJdnRArbAmWrNXSxX55fQ831N7B_R3l1c",
						false);
		Assert.assertTrue(content != null);
		System.err.println(content);
	}

	@Test
	public void upload2() throws IOException, WeixinException {
		File file = new File("/root/Pictures/2.jpg");
		MediaUploadResult mediaId = mediaApi.uploadMedia(true,
				new FileInputStream(file), file.getName());
		// 8790403529
		Assert.assertNotNull(mediaId);
		System.err.println(mediaId);
	}

	@Test
	public void uploadMaterialVideo() throws IOException, WeixinException {
		File file = new File("/Users/jy/Downloads/test.mp4");
		String mediaId = mediaApi.uploadMaterialVideo(
				new FileInputStream(file), "filename", "title", "introduction");
		// Sy1KOLsi4ri3kB3TYUuculVelcW2I7W6BrfGwkGvSW8beTCAarxuGQLjuNJChJr8
		Assert.assertNotNull(mediaId);
		System.err.println(mediaId);
	}

	@Test
	public void uploadMaterialArticle() throws WeixinException {
		List<MpArticle> articles = new ArrayList<MpArticle>();
		articles.add(new MpArticle("8790403529", "title", "content"));
		String mediaId = mediaApi.uploadMaterialArticle(articles);
		// DVWwU0u9ommOTPgyJszpK943IWCCVAcFGNmiIBObf5E
		Assert.assertNotNull(mediaId);
		System.err.println(mediaId);
	}

	@Test
	public void downloadArticle() throws WeixinException {
		List<MpArticle> articles = mediaApi.downloadArticle("DVWwU0u9ommOTPgyJszpK943IWCCVAcFGNmiIBObf5E");
		Assert.assertTrue(articles != null && !articles.isEmpty());
		System.err.println(articles);
	}

	@Test
	public void deleteMaterialMedia() throws WeixinException {
		ApiResult result = mediaApi.deleteMaterialMedia("17385064953");
		System.err.println(result);
	}

	@Test
	public void updateMaterialArticle() throws WeixinException {
		MpArticle article = new MpArticle("8790403529", "title", "content");
		article.setAuthor("author_update");
		article.setDigest("digest_update");
		article.setShowCoverPic(false);
		article.setSourceUrl("http://www.baidu.com");
		ApiResult result = mediaApi.updateMaterialArticle("17385064953", 0,
				article);
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
		Pageable pageable = Pageable.get();
		MediaRecord mediaRecord = mediaApi.listMaterialMedia(MediaType.news,
				pageable);
		System.err.println(mediaRecord);
	}

	@Test
	public void listAllMaterialMedia() throws WeixinException {
		List<MediaItem> mediaList = mediaApi
				.listAllMaterialMedia(MediaType.image);
		System.err.println(mediaList);
	}

	@Test
	public void uploadVideo() throws WeixinException {
		InputStream is = null;
		String fileName = "视频文件名";
		String title = "视频标题";
		String description = "视频描述";
		MpVideo mpVideo = mediaApi
				.uploadVideo(is, fileName, title, description);
		Assert.assertTrue(mpVideo.getMediaId() != null);
	}
}
