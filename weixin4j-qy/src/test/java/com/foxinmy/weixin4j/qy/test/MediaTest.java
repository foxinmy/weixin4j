package com.foxinmy.weixin4j.qy.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.qy.api.MediaApi;

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
		File file = new File("/tmp/test.docx");
		String mediaId = mediaApi.uploadMedia(file);
		// 1-1gpykXsR8bhNvO13-ZvskptCBxQF1UE535jFdCF63N2inGRAqEb-psF6eppjIIl
		// 1CF6sBgWWFGY9s4JCEet5ASszsTuyHpeN1f2LWXADveqBlKoxSgb3cO401NEM7dNY
		Assert.assertNotNull(mediaId);
		System.out.println(mediaId);
	}

	@Test
	public void download() throws WeixinException, IOException {
		File file = mediaApi
				.downloadMediaFile("1CF6sBgWWFGY9s4JCEet5ASszsTuyHpeN1f2LWXADveqBlKoxSgb3cO401NEM7dNY");
		Assert.assertTrue(file.exists());
	}
}