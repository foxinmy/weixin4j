package com.foxinmy.weixin4j.test;

import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.WeixinProxy;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.QRParameter;
import com.foxinmy.weixin4j.model.QRParameter.QRType;

/**
 * 二维码相关测试
 * @className QRTest
 * @author jy.hu
 * @date 2014年4月10日
 * @since JDK 1.7
 */
public class QRTest {
	private WeixinProxy weixinProxy;

	@Before
	public void init() {
		weixinProxy = new WeixinProxy();
	}

	@Test
	public void temp_qr() {
		QRParameter qr = new QRParameter(1200, QRType.QR_SCENE, 1);
		try {
			System.out.println(qr.toJson());
			weixinProxy.getQR(qr);
		} catch (WeixinException e) {
			System.out.println(e.getErrorCode());
			System.out.println(e.getErrorMsg());
		}
	}

	@Test
	public void forever_qr() {
		QRParameter qr = new QRParameter(QRType.QR_LIMIT_SCENE, 1);
		try {
			System.out.println(qr.toJson());
			weixinProxy.getQR(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
