package com.foxinmy.weixin4j.qy.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.qy.api.MediaApi;
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
	public void upload() throws IOException, WeixinException {
		File file = new File("//Users/jy/Downloads/import_file.csv");
		String mediaId = mediaApi.uploadMedia(3, file);
		// 1-1gpykXsR8bhNvO13-ZvskptCBxQF1UE535jFdCF63N2inGRAqEb-psF6eppjIIl
		// 1CF6sBgWWFGY9s4JCEet5ASszsTuyHpeN1f2LWXADveqBlKoxSgb3cO401NEM7dNY
		Assert.assertNotNull(mediaId);
		System.out.println(mediaId);
	}

	@Test
	public void download() throws WeixinException, IOException {
		File file = mediaApi
				.downloadMediaFile(3,
						"272LZlRmz1h7V2lcsvouCxwbJ_Dh-rgdDecX_26f_HDzJSZiSZjBeqeSYI1r9Ad9q66iWTGmRDUFgWOvz_fGVGi1BRZ4wjtkhPe2XcK-oomk");
		Assert.assertTrue(file.exists());
	}

	@Test
	public void listAll() throws WeixinException {
		mediaApi.listAllMaterialMedia(1, MediaType.image);
	}
}