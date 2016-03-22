package com.foxinmy.weixin4j.mp.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.QrApi;
import com.foxinmy.weixin4j.mp.model.QRParameter;
import com.foxinmy.weixin4j.mp.model.QRResult;

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
		QRResult result = qrApi.createQR(QRParameter
				.createTemporary(1200, 1200));
		Assert.assertTrue(!result.getTicket().isEmpty());
	}

	@Test
	public void forever_qr_int() throws WeixinException, IOException {
		QRResult result = qrApi.createQR(QRParameter.createPermanenceInt(2));
		Assert.assertTrue(!result.getTicket().isEmpty());
	}

	@Test
	public void forever_qr_str() throws WeixinException, IOException {
		QRResult result = qrApi.createQR(QRParameter
				.createPermanenceStr("1200中文"));
		Assert.assertTrue(!result.getTicket().isEmpty());
	}
}
