package com.foxinmy.weixin4j.qy.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.media.MediaDownloadResult;
import com.foxinmy.weixin4j.model.media.MediaUploadResult;
import com.foxinmy.weixin4j.qy.WeixinProxy;
import com.foxinmy.weixin4j.qy.api.MediaApi;
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
	public void upload() throws IOException, WeixinException {
		File file = new File("/Users/jy/Downloads/weixin4j.png");
		MediaUploadResult mediaResult = mediaApi.uploadMedia(0,
				new FileInputStream(file), file.getName());
		// 1gJ0vRLQp_o7L9hsVm6sviuGWc0qaPOd-KjkUZ6KQ7IrFVui8b2ZZd9F5szLCUkkD8gxk65lwW2SV72XO1RGZTQ
		Assert.assertNotNull(mediaResult.getMediaId());
		System.out.println(mediaResult);
	}

	@Test
	public void download() throws WeixinException, IOException {
		MediaDownloadResult result = mediaApi
				.downloadMedia(
						0,
						"1y0NWE5ochkfOoiyJsPwQ3Wg7gsyRHNp8SveqhGXY_1rOH7OcOMwfHDg8KH6s88osq59AfS3BX-MBBKvERB7Bvw");
		Assert.assertTrue(result.getContent().length > 0);
	}

	@Test
	public void listAll() throws WeixinException {
		mediaApi.listAllMaterialMedia(1, MediaType.image);
	}

	@Test
	public void uploadFile() throws IOException, WeixinException {
		File file = new File("/Users/jy/Downloads/弹性运动1.html");
		MediaUploadResult mediaResult = mediaApi.uploadMedia(0,
				new FileInputStream(file), "弹性运动1.html");
		Assert.assertNotNull(mediaResult.getMediaId());
		System.out.println(mediaResult);
	}

	@Test
	public void downloadFile() throws WeixinException {
		MediaDownloadResult result = mediaApi
				.downloadMedia(
						0,
						"19pXNIq8cd69QLwfsLaoZFfS2K82WCHNGPREO--o1rEMlNIOf0N9IDDQdler08S7fNAFsG-5XYwxf1gzORxDnlQ");
		System.err.println(result);
	}
	
	public static void main(String[] args) throws FileNotFoundException, WeixinException{
		WeixinProxy proxy = new WeixinProxy();
		File file = new File("/Users/jy/Downloads/weixin4j.png");
		String mediaResult = proxy.uploadImage(
				new FileInputStream(file), file.getName());
		System.out.println(mediaResult);
	}
}