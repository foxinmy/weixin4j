package com.foxinmy.weixin4j.test;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 媒体上传下载测试
 * @className MediaTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 * @see
 */
public class MediaTest {

	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		weixinProxy = new WeixinProxy();
	}

	@Test
	public void upload() {
		File file = new File("D:\\test.jpg");
		try {
			System.out.println(weixinProxy.uploadMedia(file, MediaType.image));
			//vvU_AUtovWyfAxQ8J1DsCoNMtK6U_bUmTpe6lpINUOVRLvt_7rtO4zxzBpPgkmay
		} catch (WeixinException e) {
			System.out.println(e.getErrorMsg());
		}
	}
	
	@Test
	public void download() {
		try {
			System.out.println(weixinProxy.downloadMedia("vvU_AUtovWyfAxQ8J1DsCoNMtK6U_bUmTpe6lpINUOVRLvt_7rtO4zxzBpPgkmay", MediaType.image));
		} catch (WeixinException e) {
			System.out.println(e.getErrorMsg());
		}
	}
}
