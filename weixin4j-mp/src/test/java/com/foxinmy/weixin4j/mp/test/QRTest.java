package com.foxinmy.weixin4j.mp.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.qr.QRParameter;
import com.foxinmy.weixin4j.model.qr.QRResult;
import com.foxinmy.weixin4j.mp.api.QrApi;

/**
 * 二维码相关测试
 * 
 * @className QRTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月10日
 * @since JDK 1.6
 */
public class QRTest extends TokenTest {
	private QrApi qrApi;

	@Before
	public void init() throws WeixinException {
		qrApi = new QrApi(tokenManager);
	}

	@Test
	public void temp_qr() throws WeixinException, IOException {
		QRResult result = qrApi.createQR(QRParameter.createTemporaryQR(1200,
				1200L));
		Assert.assertTrue(!result.getTicket().isEmpty());
	}

	@Test
	public void forever_qr_int() throws WeixinException, IOException {
		QRResult result = qrApi.createQR(QRParameter.createPermanenceQR(2));
		Assert.assertTrue(!result.getTicket().isEmpty());
	}

	@Test
	public void forever_qr_str() throws WeixinException, IOException {
		QRResult result = qrApi.createQR(QRParameter
				.createPermanenceQR("1200中文"));
		Assert.assertTrue(!result.getTicket().isEmpty());
	}
}
