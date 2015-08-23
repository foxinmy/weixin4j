package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.PayException;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.mp.api.Pay2Api;
import com.foxinmy.weixin4j.payment.PayUtil;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.foxinmy.weixin4j.payment.mch.ApiResult;
import com.foxinmy.weixin4j.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.payment.mch.PrePay;
import com.foxinmy.weixin4j.token.FileTokenStorager;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;

public class PayTest {
	protected final static Pay2Api PAY2;
	protected final static WeixinPayProxy PAY3;
	protected final static WeixinPayAccount ACCOUNT2;
	protected final static WeixinPayAccount ACCOUNT3;
	static {
		ACCOUNT2 = new WeixinPayAccount("请填入v2版本的appid", "请填入v2版本的appSecret",
				"请填入v2版本的paysignkey", null, null, null, "请填入v2版本的partnerId",
				"请填入v2版本的partnerKey");
		PAY2 = new Pay2Api(ACCOUNT2, new FileTokenStorager(
				Weixin4jConfigUtil
						.getValue("token_path", "/tmp/weixin4j/token")));
		ACCOUNT3 = new WeixinPayAccount("请填入v3版本的appid", "请填入v3版本的appSecret",
				"请填入v3版本的paysignkey", "请填入v3版本的mchid", null, null, null, null);
		PAY3 = new WeixinPayProxy(ACCOUNT3);
	}
	/**
	 * 商户证书文件
	 */
	protected File caFile = new File("证书文件，如12333.p12");

	@Test
	public void orderQueryV2() throws WeixinException {
		System.err.println(PAY2.orderQuery(new IdQuery("D14110500021",
				IdType.REFUNDNO)));
	}

	@Test
	public void refundV2() throws WeixinException {
		File caFile = new File("证书文件，如12333.pfx");
		IdQuery idQuery = new IdQuery("D15020300005", IdType.TRADENO);
		System.err.println(PAY2.refundApply(caFile, idQuery, "1422925555037",
				16d, 16d, "1221928801", "111111", null, null, null));
	}

	@Test
	public void refundQueryV2() throws WeixinException {
		System.err.println(PAY2.refundQuery(new IdQuery("D14123000004",
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
		Order order = PAY3.orderQuery(new IdQuery("T0002", IdType.TRADENO));
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
		com.foxinmy.weixin4j.payment.mch.RefundRecord record = PAY3
				.refundQueryV3(new IdQuery("TT_1427183696238", IdType.TRADENO));
		System.err.println(record);
		// 这里的验证签名需要把details循环拼接
		String sign = record.getSign();
		record.setSign(null);
		String valiSign = PayUtil.paysignMd5(record, ACCOUNT3.getPaySignKey());
		System.err
				.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
		Assert.assertEquals(valiSign, sign);
	}

	@Test
	public void downbillV3() throws WeixinException {
		Calendar c = Calendar.getInstance();
		System.err.println(c.getTime());
		c.set(Calendar.YEAR, 2015);
		c.set(Calendar.MONTH, 2);
		c.set(Calendar.DAY_OF_MONTH, 24);
		System.err.println(c.getTime());
		File file = PAY3.downloadbill(c.getTime(), null);
		System.err.println(file);
	}

	@Test
	public void refundV3() throws WeixinException, IOException {
		File caFile = new File("签名文件如123.p12");
		IdQuery idQuery = new IdQuery("TT_1427183696238", IdType.TRADENO);
		com.foxinmy.weixin4j.payment.mch.RefundResult result = PAY3
				.refundApply(new FileInputStream(caFile), idQuery, "TT_R"
						+ System.currentTimeMillis(), 0.01d, 0.01d, null,
						"10020674");
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
		MchPayPackage payPackageV3 = new MchPayPackage(ACCOUNT3,
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
