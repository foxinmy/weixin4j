package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.QrApi;
import com.foxinmy.weixin4j.mp.model.QRParameter;

/**
 * 二维码相关测试
 * 
 * @className QRTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class QRTest extends TokenTest {
	private QrApi qrApi;

	@Before
	public void init() {
		qrApi = new QrApi(tokenHolder);
	}

	@Test
	public void temp_qr() throws WeixinException, IOException {
		File file = qrApi.createQRFile(QRParameter.createTemporary(1200, 1200),
				"/tmp/weixin4j/qrcode");
		Assert.assertTrue(file.exists());
	}

	@Test
	public void forever_qr_int() throws WeixinException, IOException {
		File file = qrApi.createQRFile(QRParameter.createPermanenceInt(2),
				"/tmp/weixin4j/qrcode");
		Assert.assertTrue(file.exists());
	}

	@Test
	public void forever_qr_str() throws WeixinException, IOException {
		File file = qrApi.createQRFile(
				QRParameter.createPermanenceStr("1200中文"),
				"/tmp/weixin4j/qrcode");
		Assert.assertTrue(file.exists());
	}
}
