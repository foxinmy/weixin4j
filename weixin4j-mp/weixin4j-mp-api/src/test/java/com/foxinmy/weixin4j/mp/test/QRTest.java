package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.QrApi;
import com.foxinmy.weixin4j.mp.model.QRParameter;
import com.foxinmy.weixin4j.mp.model.QRParameter.QRType;

/**
 * 二维码相关测试
 * 
 * @className QRTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class QRTest extends TokenTest {
	private QrApi qrApi;

	@Before
	public void init() {
		qrApi = new QrApi(tokenHolder);
	}

	@Test
	public void temp_qr() throws WeixinException, IOException {
		QRParameter qr = new QRParameter(1200, QRType.TEMPORARY, 2);
		File file = qrApi.getQR(qr);
		Assert.assertTrue(file.exists());
	}

	@Test
	public void forever_qr() throws WeixinException, IOException {
		QRParameter qr = new QRParameter(QRType.PERMANENCE, 1);
		File file = qrApi.getQR(qr);
		Assert.assertTrue(file.exists());
	}
}
