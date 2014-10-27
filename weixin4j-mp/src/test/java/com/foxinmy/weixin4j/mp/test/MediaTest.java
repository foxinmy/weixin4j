package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.MediaApi;
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
		mediaApi = new MediaApi(tokenApi);
	}

	@Test
	public void upload() throws IOException, WeixinException {
		File file = new File("/tmp/test.jpg");
		String mediaId = mediaApi.uploadMedia(file, MediaType.image);
		// vvU_AUtovWyfAxQ8J1DsCoNMtK6U_bUmTpe6lpINUOVRLvt_7rtO4zxzBpPgkmay
		Assert.assertNotNull(mediaId);
	}

	@Test
	public void download() throws WeixinException, IOException {
		File file = mediaApi
				.downloadMedia(
						"vvU_AUtovWyfAxQ8J1DsCoNMtK6U_bUmTpe6lpINUOVRLvt_7rtO4zxzBpPgkmay",
						MediaType.image);
		Assert.assertTrue(file.exists());
	}
}
