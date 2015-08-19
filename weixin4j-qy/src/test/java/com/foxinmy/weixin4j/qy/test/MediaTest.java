package com.foxinmy.weixin4j.qy.test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.MediaUploadResult;
import com.foxinmy.weixin4j.qy.api.MediaApi;
import com.foxinmy.weixin4j.type.MediaType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
	public void upload() throws IOException, WeixinException {
		File file = new File("D:\\workspace2015\\0.png");
		MediaUploadResult mediaResult = mediaApi.uploadMedia(0, new FileInputStream(
				file), file.getName());
		// 1-1gpykXsR8bhNvO13-ZvskptCBxQF1UE535jFdCF63N2inGRAqEb-psF6eppjIIl
		// 1CF6sBgWWFGY9s4JCEet5ASszsTuyHpeN1f2LWXADveqBlKoxSgb3cO401NEM7dNY
		Assert.assertNotNull(mediaResult.getMediaId());
		System.out.println(mediaResult);
	}

	@Test
	public void uploadFile() throws IOException, WeixinException {
		File file = new File("D:\\workspace2015\\1.txt");
		MediaUploadResult mediaResult = mediaApi.uploadMedia(0, new FileInputStream(
				file), file.getName());
		// 1-1gpykXsR8bhNvO13-ZvskptCBxQF1UE535jFdCF63N2inGRAqEb-psF6eppjIIl
		// 1CF6sBgWWFGY9s4JCEet5ASszsTuyHpeN1f2LWXADveqBlKoxSgb3cO401NEM7dNY
		Assert.assertNotNull(mediaResult.getMediaId());
		System.out.println(mediaResult);
	}

	@Test
	public void download() throws WeixinException, IOException {
		File file = mediaApi
				.downloadMediaFile(
						0,
						"1FnZZ7XJPrzdxmJbgp-KrLsLWyZjqrCh_NpTRW_xBq7UdIs_BUjC4pNk1KoSfjyXjojLznWvIlu1-4vkjaQt-JQ");
		Assert.assertTrue(file.exists());
	}

	@Test
	public void listAll() throws WeixinException {
		mediaApi.listAllMaterialMedia(1, MediaType.image);
	}
}