package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.XmlResult;
import com.foxinmy.weixin4j.model.WeixinMpAccount;
import com.foxinmy.weixin4j.mp.WeixinPayProxy;
import com.foxinmy.weixin4j.mp.payment.PayUtil;
import com.foxinmy.weixin4j.mp.payment.v3.ApiResult;
import com.foxinmy.weixin4j.mp.payment.v3.Order;
import com.foxinmy.weixin4j.mp.payment.v3.PayPackageV3;
import com.foxinmy.weixin4j.mp.payment.v3.PrePay;
import com.foxinmy.weixin4j.mp.type.IdQuery;
import com.foxinmy.weixin4j.mp.type.IdType;
import com.foxinmy.weixin4j.mp.type.TradeType;
import com.foxinmy.weixin4j.token.FileTokenHolder;
import com.foxinmy.weixin4j.token.WeixinTokenCreator;
import com.foxinmy.weixin4j.type.AccountType;

public class PayTest {
	private final static WeixinPayProxy PAY2;
	private final static WeixinPayProxy PAY3;
	private final static WeixinMpAccount ACCOUNT2;
	private final static WeixinMpAccount ACCOUNT3;
	static {
		ACCOUNT2 = new WeixinMpAccount(
				"appId",
				"appSecret",
				"paySignKey",
				"partnerId", "partnerKey");
		PAY2 = new WeixinPayProxy(ACCOUNT2, new FileTokenHolder(
				new WeixinTokenCreator(ACCOUNT2.getId(), ACCOUNT2.getSecret(),
						AccountType.MP)));
		ACCOUNT3 = new WeixinMpAccount("appId",
				"appSecret",
				"paySignKey", "mchId");
		PAY3 = new WeixinPayProxy(ACCOUNT3, new FileTokenHolder(
				new WeixinTokenCreator(ACCOUNT3.getId(), ACCOUNT3.getSecret(),
						AccountType.MP)));
	}

	@Test
	public void orderQueryV2() throws WeixinException {
		System.err.println(PAY2.orderQueryV2("D14110500021"));
	}

	@Test
	public void refundV2() throws WeixinException {
		File caFile = new File(
				"/Users/jy/download/1221928801.pfx");
		IdQuery idQuery = new IdQuery("D15012400026", IdType.TRADENO);
		System.err.println(PAY2.refundV2(caFile, idQuery, "R000000001", 2d, 2d,
				"1221928801", "111111", null, null, null));
	}

	@Test
	public void refundQueryV2() throws WeixinException {
		System.err.println(PAY2.refundQueryV2(new IdQuery("D14123000004",
				IdType.TRADENO)));
		refundQueryV3();
	}

	@Test
	public void downbillV2() throws WeixinException {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2014);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 22);
		File file = PAY2.downloadbill(c.getTime(), null);
		System.err.println(file);
	}

	@Test
	public void orderQueryV3() throws WeixinException {
		Order order = PAY3.orderQueryV3(new IdQuery("T0002", IdType.TRADENO));
		System.err.println(order);
		String sign = order.getSign();
		order.setSign(null);
		String valiSign = PayUtil.paysignMd5(order, ACCOUNT3.getPaySignKey());
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void refundQueryV3() throws WeixinException {
		System.err.println(PAY3.refundQueryV3(new IdQuery("T00015",
				IdType.TRADENO)));
	}

	@Test
	public void downbillV3() throws WeixinException {
		Calendar c = Calendar.getInstance();
		System.err.println(c.getTime());
		c.set(Calendar.YEAR, 2014);
		c.set(Calendar.MONTH, 9);
		c.set(Calendar.DAY_OF_MONTH, 22);
		System.err.println(c.getTime());
		File file = PAY3.downloadbill(c.getTime(), null);
		System.err.println(file);
	}

	@Test
	public void refundV3() throws WeixinException {
		File caFile = new File(
				"/Users/jy/download/10020674.p12");
		IdQuery idQuery = new IdQuery("T00015", IdType.TRADENO);
		com.foxinmy.weixin4j.mp.payment.v3.RefundResult result = PAY3.refundV3(
				caFile, idQuery, "R0002", 1d, 1d, "10020674");
		System.err.println(result);
		String sign = result.getSign();
		result.setSign(null);
		String valiSign = PayUtil.paysignMd5(result, ACCOUNT3.getPaySignKey());
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void nativeV3() throws WeixinException {
		PayPackageV3 payPackageV3 = new PayPackageV3(ACCOUNT3,
				"oyFLst1bqtuTcxK-ojF8hOGtLQao", "native测试", "T0001", 0.1d,
				"127.0.0.1", TradeType.NATIVE);
		payPackageV3.setProductId("0001");
		payPackageV3.setNotifyUrl("xxxx");
		PrePay prePay = null;
		try {
			prePay = PayUtil.createPrePay(payPackageV3,
					ACCOUNT3.getPaySignKey());
		} catch (PayException e) {
			e.printStackTrace();
		}
		System.err.println(prePay);
	}

	@Test
	public void closeOrder() throws WeixinException {
		ApiResult result = PAY3.closeOrder("D111");
		System.err.println(result);
		String sign = result.getSign();
		result.setSign(null);
		String valiSign = PayUtil.paysignMd5(result, ACCOUNT3.getPaySignKey());
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void shortUrl() throws WeixinException {
		String url = "weixin://wxpay/bizpayurl?xxxxxx";
		String shortUrl = PAY3.getPayShorturl(url);
		System.err.println(shortUrl);
	}

	@Test
	public void interfaceReport() throws WeixinException {
		String interfaceUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		int executeTime = 2500;
		String outTradeNo = null;
		String ip = "127.0.0.1";
		Date time = new Date();
		XmlResult returnXml = new XmlResult("SUCCESS", "");
		returnXml.setResultCode("SUCCESS");
		returnXml = PAY3.interfaceReport(interfaceUrl, executeTime, outTradeNo,
				ip, time, returnXml);
		System.err.println(returnXml);
	}
}
